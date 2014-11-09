package de.oetting.bumpingbunnies.pc.mainMenu;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.input.NoopInputConfiguration;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NoopSocket;
import de.oetting.bumpingbunnies.core.network.WlanDevice;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.core.networking.LocalPlayerEntry;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerEstablisher;
import de.oetting.bumpingbunnies.core.networking.client.CouldNotOpenBroadcastSocketException;
import de.oetting.bumpingbunnies.core.networking.client.DisplaysConnectedServers;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.pc.ApplicationStarter;
import de.oetting.bumpingbunnies.pc.configMenu.ConfigApplication;
import de.oetting.bumpingbunnies.pc.configMenu.PcConfiguration;
import de.oetting.bumpingbunnies.pc.configMenu.PlayerConfiguration;
import de.oetting.bumpingbunnies.pc.configuration.ConfigAccess;
import de.oetting.bumpingbunnies.pc.configuration.PcConfigurationConverter;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;
import de.oetting.bumpingbunnies.pc.network.messaging.PcGameStopper;

public class MainMenuController implements Initializable, OnBroadcastReceived, ConnectsToServer, DisplaysConnectedServers, PlayerDisconnectedCallback,
		ThreadErrorCallback {

	private final Stage primaryStage;
	@FXML
	private Button withTwoPlayersButton;
	@FXML
	private Button withAiButton;
	@FXML
	private Button connectButton;
	@FXML
	private Button addPlayerButton;
	@FXML
	private TableView<Host> hostsTable;
	@FXML
	private TableView<RoomEntry> playersTable;

	private ListenForBroadcastsThread listenForBroadcastsThread;

	// Lazily loaded. only access via getConfiguration
	private PcConfiguration configuration;

	public MainMenuController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	public void onButtonWithTwoPlayers(ActionEvent event) {
		startGameWithTwoPlayers();
	}

	@FXML
	public void onButtonWithAi(ActionEvent event) {
		startGameWithAi();
	}

	private void startGameWithTwoPlayers() {
		KeyboardInputConfiguration configuration2 = new PcConfigurationConverter().createConfiguration(getConfiguration().getPlayer2Configuration());
		OpponentConfiguration opponentConfiguration = new OpponentConfiguration(AiModus.OFF, new PlayerProperties(1, "Player 2"),
				ConnectionIdentifierFactory.createLocalPlayer("Player2"), configuration2);
		Configuration configuration = createConfiguration(Arrays.asList(opponentConfiguration));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private Configuration createConfiguration(List<OpponentConfiguration> opponentsFoo) {
		List<OpponentConfiguration> opponents = readOpponents();
		PcConfiguration pcConfiguration = new ConfigAccess().load();
		LocalSettings localSettings = new PcConfigurationConverter().convert2LocalSettings(getConfiguration());
		ServerSettings generalSettings = new PcConfigurationConverter().convert2ServerSettings(pcConfiguration);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings();
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, localPlayerSettings, true);
		return configuration;
	}

	private List<OpponentConfiguration> readOpponents() {
		List<OpponentConfiguration> opponents = new ArrayList<>(playersTable.getItems().size());
		for (RoomEntry entry : playersTable.getItems()) {
			if (!(entry instanceof LocalPlayerEntry))
				opponents.add(createOpponentConfiguration(entry));
		}
		return opponents;
	}

	private OpponentConfiguration createOpponentConfiguration(RoomEntry entry) {
		if (entry.getOponent().isLocalHumanPlayer()) {
			PlayerConfiguration playerConfiguration = findPlayerConfiguration(entry);
			return new OpponentConfiguration(AiModus.OFF, entry.getPlayerProperties(), entry.getOponent(),
					new PcConfigurationConverter().createConfiguration(playerConfiguration));
		} else if (entry.getOponent().isLocalPlayer()) {
			return new OpponentConfiguration(AiModus.NORMAL, entry.getPlayerProperties(), entry.getOponent(), new NoopInputConfiguration());
		} else {
			return new OpponentConfiguration(AiModus.OFF, entry.getPlayerProperties(), entry.getOponent(), new NoopInputConfiguration());
		}
	}

	private PlayerConfiguration findPlayerConfiguration(RoomEntry entry) {
		for (PlayerConfiguration configuration : getConfiguration().getPlayerConfigurations()) {
			if (configuration.getPlayerName().equals(entry.getPlayerName())) {
				return configuration;
			}
		}
		throw new IllegalArgumentException(String.format(
				"Could not find player in list of player configurations. I looked for player with name %s. Existing configurations are %s",
				entry.getPlayerName(), getConfiguration().getPlayerConfigurations()));
	}

	private void startGameWithAi() {
		OpponentConfiguration aiOpponent = new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(1, "Player 2"),
				ConnectionIdentifierFactory.createAiPlayer("Player2"), new NoopInputConfiguration());
		Configuration configuration = createConfiguration(Arrays.asList(aiOpponent));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private void startGame(GameStartParameter parameter) {
		listenForBroadcastsThread.stopListening();
		new ApplicationStarter().startApplication(new BunniesMain(parameter), primaryStage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hostsTable.getSelectionModel().selectedIndexProperty().addListener(event -> connectButton.setDisable(false));
		listenForBroadcasts();
		addMyPlayerRoomEntry(0);
	}

	private void listenForBroadcasts() {
		try {
			listenForBroadcastsThread = ListenforBroadCastsThreadFactory.create(this, new PcGameStopper());
			listenForBroadcastsThread.start();
		} catch (CouldNotOpenBroadcastSocketException e) {
			hostsTable.getItems().add(new Host("Cannot listen to broadcasts..."));
		}
	}

	@Override
	public void broadcastReceived(InetAddress senderAddress) {
		Host host = new Host(senderAddress);
		if (!hostsTable.getItems().contains(host))
			hostsTable.getItems().add(host);
	}

	@FXML
	public void onButtonConnect() {
		playersTable.getItems().clear();
		WlanDevice wlanDevice = new WlanDevice(hostsTable.getSelectionModel().getSelectedItem().getAddress());
		MySocket socket = wlanDevice.createClientSocket();
		ConnectionToServerEstablisher connectToServerThread = new ConnectionToServerEstablisher(socket, this);
		connectToServerThread.start();
	}

	@FXML
	public void onButtonAddAi() {
		String playerName = "AI" + getNextPlayerId();
		PlayerProperties properties = new PlayerProperties(getNextPlayerId(), playerName);
		addPlayerEntry(new NoopSocket(ConnectionIdentifierFactory.createAiPlayer(playerName)), properties, 0);
		enableButtons();
	}

	@FXML
	public void onButtonAddPlayer() {
		int nextIndex = getNextPlayerId();
		PlayerConfiguration playerConfiguration = findNextFreePlayerConfiguration();
		PlayerProperties properties = new PlayerProperties(nextIndex, playerConfiguration.getPlayerName());
		addPlayerEntry(new NoopSocket(ConnectionIdentifierFactory.createLocalPlayer(properties.getPlayerName())), properties, 0);
		enableButtons();
	}

	private PlayerConfiguration findNextFreePlayerConfiguration() {
		int index = findNextFreePlayerId();
		return getConfiguration().getPlayerConfiguration(index);
	}

	private int findNextFreePlayerId() {
		int index = 0;
		for (RoomEntry entry : playersTable.getItems()) {
			if (entry.getOponent().isLocalHumanPlayer()) {
				index++;
			}
		}
		return index;
	}

	private int getNextPlayerId() {
		return playersTable.getItems().size();
	}

	@FXML
	public void onButtonConfiguration() {
		new ApplicationStarter().startApplication(new ConfigApplication(), primaryStage);
	}

	@Override
	public void connectionNotSuccesful(String message) {
		Platform.exit();
	}

	@Override
	public void connectToServerSuccesfull(MySocket mmSocket) {
		SetupConnectionWithServer connectedToServerService = new SetupConnectionWithServer(mmSocket, this, this, this, this);
		connectedToServerService.onConnectionToServer();
	}

	@Override
	public LocalPlayerSettings createLocalPlayerSettings() {
		return new PcConfigurationConverter().convert2LocalPlayerSettings(getConfiguration());
	}

	@Override
	public void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex) {
		RoomEntry entry = createRoomEntry(serverSocket, properties);
		playersTable.getItems().add(entry);
		enableButtons();
	}

	private RoomEntry createRoomEntry(MySocket socket, PlayerProperties playerProperties) {
		if (socket.getConnectionIdentifier().isLocalPlayer())
			return new RoomEntry(playerProperties, socket.getConnectionIdentifier());
		if (socket.getConnectionIdentifier().isDirectlyConnected())
			return new RoomEntry(playerProperties, socket.getConnectionIdentifier());
		else
			return new RoomEntry(playerProperties, ConnectionIdentifierFactory.createJoinedPlayer(playerProperties.getPlayerName(),
					playerProperties.getPlayerId()));
	}

	@Override
	public void addMyPlayerRoomEntry(int myPlayerId) {
		LocalPlayerSettings settings = createLocalPlayerSettings();
		PlayerProperties singlePlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
		playersTable.getItems().add(new LocalPlayerEntry(singlePlayerProperties));
		enableButtons();
	}

	@Override
	public void launchGame(ServerSettings generalSettingsFromNetwork, boolean asHost) {
		PcConfiguration pcConfiguration = new ConfigAccess().load();
		LocalSettings localSettings = new PcConfigurationConverter().convert2LocalSettings(pcConfiguration);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings();
		int myPlayerId = getLocalPlayerId();
		List<OpponentConfiguration> otherPlayers = createOtherPlayerconfigurations();
		Configuration config = new Configuration(localSettings, generalSettingsFromNetwork, otherPlayers, localPlayerSettings, asHost);
		GameStartParameter parameter = GameParameterFactory.createParameter(myPlayerId, config);
		startGame(parameter);
	}

	private int getLocalPlayerId() {
		for (RoomEntry entry : playersTable.getItems()) {
			if (entry instanceof LocalPlayerEntry) {
				return entry.getPlayerId();
			}
		}
		throw new IllegalArgumentException("Local player could not be found");
	}

	private List<OpponentConfiguration> createOtherPlayerconfigurations() {
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>();
		for (RoomEntry otherPlayer : playersTable.getItems()) {
			if (!otherPlayer.getOponent().isLocalPlayer()) {
				OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(AiModus.NORMAL, otherPlayer.getPlayerProperties(),
						otherPlayer.getOponent(), new NoopInputConfiguration());
				otherPlayers.add(otherPlayerConfiguration);
			}
		}
		return otherPlayers;
	}

	@Override
	public void playerDisconnected(ConnectionIdentifier opponent) {
		RoomEntry entry = findDisconnectedPlayerEntry(opponent);
		playersTable.getItems().remove(entry);
	}

	@Override
	public void playerDisconnected(int playerId) {
		RoomEntry entry = findDisconnectedPlayerEntry(playerId);
		playersTable.getItems().remove(entry);
	}

	private RoomEntry findDisconnectedPlayerEntry(ConnectionIdentifier opponent) {
		for (RoomEntry entry : playersTable.getItems()) {
			if (entry.getOponent().equals(opponent))
				return entry;
		}
		throw new IllegalArgumentException("Player does not exist");
	}

	private RoomEntry findDisconnectedPlayerEntry(int playerId) {
		for (RoomEntry entry : playersTable.getItems()) {
			if (entry.getPlayerId() == playerId)
				return entry;
		}
		throw new IllegalArgumentException("Player does not exist");
	}

	@FXML
	public void onHostButton() {
		Configuration configuration = createConfiguration(new ArrayList<>());
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	@Override
	public void onThreadError() {
		Platform.exit();
	}

	private synchronized PcConfiguration getConfiguration() {
		if (configuration == null)
			configuration = new ConfigAccess().load();
		Guard.againstNull(configuration);
		return configuration;
	}

	public void enableButtons() {
		Platform.runLater(() -> addPlayerButton.setDisable(findNextFreePlayerId() >= getConfiguration().getMaxNumberOfPlayers()));
	}

	public void onMouseClickOnPlayers(MouseEvent event) {
		if (event.getClickCount() > 1) {
			TablePosition<?, ?> focusedCell = playersTable.getFocusModel().getFocusedCell();
			if (focusedCell.getRow() != -1) {
				event.consume();
				if (!(playersTable.getItems().get(focusedCell.getRow()) instanceof LocalPlayerEntry))
					playersTable.getItems().remove(focusedCell.getRow());
			}
			enableButtons();
		}
	}

	public void onButtonClose() {
		Platform.exit();
	}
}