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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
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
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentFactory;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;

public class MainMenuController implements Initializable, OnBroadcastReceived, ConnectsToServer, DisplaysConnectedServers, PlayerDisconnectedCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);

	private final Stage primaryStage;
	@FXML
	private Button withTwoPlayersButton;
	@FXML
	private Button withAiButton;
	@FXML
	private Button connectButton;
	@FXML
	private TableView<Host> hostsTable;
	@FXML
	private TableView<RoomEntry> playersTable;

	private ListenForBroadcastsThread listenForBroadcastsThread;

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
		Configuration configuration = createConfiguration(new OpponentConfiguration(AiModus.OFF, new PlayerProperties(1, "Player 2"),
				OpponentFactory.createLocalPlayer("Player2")));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private Configuration createConfiguration(OpponentConfiguration opponent) {
		LocalSettings localSettings = createLocalSettings();
		ServerSettings generalSettings = new ServerSettings(WorldConfiguration.CLASSIC, 25, NetworkType.WLAN);
		List<OpponentConfiguration> opponents = Arrays.asList(opponent);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings();
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, localPlayerSettings, true);
		return configuration;
	}

	private LocalSettings createLocalSettings() {
		return new LocalSettings(InputConfiguration.KEYBOARD, 1, true, false);
	}

	private void startGameWithAi() {
		Configuration configuration = createConfiguration(new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(1, "Player 2"),
				OpponentFactory.createAiPlayer("Player2")));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private void startGame(GameStartParameter parameter) {
		listenForBroadcastsThread.stopListening();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					BunniesMain bunniesMain = new BunniesMain(parameter);
					bunniesMain.start(primaryStage);
				} catch (Exception e) {
					LOGGER.error("", e);
					Platform.exit();
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hostsTable.getSelectionModel().selectedIndexProperty().addListener(event -> connectButton.setDisable(false));
		listenForBroadcasts();
	}

	private void listenForBroadcasts() {
		try {
			listenForBroadcastsThread = ListenforBroadCastsThreadFactory.create(this);
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

	@Override
	public void errorOnBroadcastListening() {
		Platform.exit();
	}

	public void onButtonConnect() {
		WlanDevice wlanDevice = new WlanDevice(hostsTable.getSelectionModel().getSelectedItem().getAddress());
		MySocket socket = wlanDevice.createClientSocket();
		ConnectionToServerEstablisher connectToServerThread = new ConnectionToServerEstablisher(socket, this);
		connectToServerThread.start();
	}

	@Override
	public void connectionNotSuccesful(String message) {
		Platform.exit();
	}

	@Override
	public void connectToServerSuccesfull(MySocket mmSocket) {
		SetupConnectionWithServer connectedToServerService = new SetupConnectionWithServer(mmSocket, this, this, this);
		connectedToServerService.onConnectionToServer();
	}

	@Override
	public LocalPlayerSettings createLocalPlayerSettings() {
		return new LocalPlayerSettings("test");
	}

	@Override
	public void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex) {
		RoomEntry entry = new RoomEntry(properties, serverSocket.getOwner());
		playersTable.getItems().add(entry);
	}

	@Override
	public void addMyPlayerRoomEntry(int myPlayerId) {
		LocalPlayerSettings settings = createLocalPlayerSettings();
		PlayerProperties localPlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
		playersTable.getItems().add(new LocalPlayerEntry(localPlayerProperties));
	}

	@Override
	public void launchGame(ServerSettings generalSettingsFromNetwork, boolean asHost) {

		LocalSettings localSettings = createLocalSettings();
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
			if (!otherPlayer.getPlayerName().equals(createLocalPlayerSettings().getPlayerName())) {
				AiModus aiMode = AiModus.NORMAL;

				OpponentConfiguration otherPlayerConfiguration = new OpponentConfiguration(aiMode, otherPlayer.getPlayerProperties(),
						otherPlayer.createOponent());
				otherPlayers.add(otherPlayerConfiguration);
			}
		}
		return otherPlayers;
	}

	@Override
	public void playerDisconnected(Opponent opponent) {
		RoomEntry entry = findDisconnectedPlayerEntry(opponent);
		playersTable.getItems().remove(entry);
	}

	private RoomEntry findDisconnectedPlayerEntry(Opponent opponent) {
		for (RoomEntry entry : playersTable.getItems()) {
			if (entry.getPlayerName().equals(opponent.getIdentifier())) {
				return entry;
			}
		}
		throw new IllegalArgumentException("Player does not exist");
	}

}