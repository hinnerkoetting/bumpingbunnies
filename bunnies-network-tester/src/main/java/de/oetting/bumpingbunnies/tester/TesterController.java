package de.oetting.bumpingbunnies.tester;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.RoomEntry;
import de.oetting.bumpingbunnies.core.network.WlanDevice;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.networking.SinglePlayerRoomEntry;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerService;
import de.oetting.bumpingbunnies.core.networking.client.DisplaysConnectedServers;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.ToServerConnector;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadSender;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreSender;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameSender;
import de.oetting.bumpingbunnies.core.networking.sender.SendRemoteSettingsSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class TesterController implements Initializable, OnBroadcastReceived, DisplaysConnectedServers, ConnectsToServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TesterController.class);

	@FXML
	private TableView<Host> broadcastTable;
	@FXML
	private TableView<RoomEntry> playersTable;
	@FXML
	private javafx.scene.control.TextField myPlayerNameTextfield;
	@FXML
	private TextField killPlayerIdTextfield;
	@FXML
	private TextField sendSpawnpointPlayerId;
	@FXML
	private TextField spawnpointX;
	@FXML
	private TextField spawnpointY;
	@FXML
	private TextField playerScoreIdTextfield;

	private ListenForBroadcastsThread listenForBroadcasts;
	private MySocket socketToServer;

	@FXML
	TextField playerScoreTextfield;

	public void initialize(URL location, ResourceBundle resources) {
		listenForBroadcasts = ListenforBroadCastsThreadFactory.create(this);
		listenForBroadcasts.start();
	}

	public void broadcastReceived(InetAddress senderAddress) {
		Host host = new Host(senderAddress);
		if (!broadcastTable.getItems().contains(host))
			broadcastTable.getItems().add(host);
	}

	public void errorOnBroadcastListening() {
		Platform.exit();
	}

	@FXML
	public void onButtonConnect() {
		ReadOnlyObjectProperty<Host> selectedItem = broadcastTable.getSelectionModel().selectedItemProperty();
		SocketFactory factory = new WlanSocketFactory();
		WlanDevice wlanDevice = new WlanDevice(selectedItem.getValue().getAddress());
		ToServerConnector connectToServerThread = new ToServerConnector(factory.createClientSocket(wlanDevice), this);
		connectToServerThread.start();
	}

	public void connectionNotSuccesful(String message) {
		Platform.exit();
	}

	public void connectToServerSuccesfull(MySocket mmSocket) {
		this.socketToServer = mmSocket;
		LOGGER.info("Connected to server %s", mmSocket);
		ConnectionToServerService connectedToServerService = new ConnectionToServerService(mmSocket, this);
		connectedToServerService.onConnectionToServer();

		// new Thread(new LogMessagesFromSocket(mmSocket)).start();

		// SimpleNetworkSender sender = new
		// SimpleNetworkSender(MessageParserFactory.create(), mmSocket);
		// SendRemoteSettingsSender remoteSettingsSender = new
		// SendRemoteSettingsSender(sender);
		// RemoteSettings remoteSettings = new RemoteSettings(new
		// FreePortFinder().findFreePort(), "test");
		// remoteSettingsSender.sendMessage(remoteSettings);
		// PlayerStateSender stateSender = new PlayerStateSender(sender);
		// Player myPlayer = new Player(1, "test", 1, new Opponent("",
		// OpponentType.LOCAL_PLAYER));
		// myPlayer.setCenterX(ModelConstants.STANDARD_WORLD_SIZE / 2);
		// stateSender.sendState(myPlayer);
	}

	public void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex) {
		RoomEntry entry = new RoomEntry(properties, serverSocket, socketIndex);
		playersTable.getItems().add(entry);
	}

	public void addMyPlayerRoomEntry(int myPlayerId) {
		LocalPlayerSettings settings = createLocalPlayerSettings();
		PlayerProperties singlePlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
		playersTable.getItems().add(new SinglePlayerRoomEntry(singlePlayerProperties));
	}

	public LocalPlayerSettings createLocalPlayerSettings() {
		return new LocalPlayerSettings(myPlayerNameTextfield.getText());
	}

	public void launchGame(GeneralSettings generalSettingsFromNetwork, boolean asHost) {
	}

	@FXML
	public void onButtonSendRemoteSettings() {
		SimpleNetworkSender sender = createNetworkSender();
		SendRemoteSettingsSender remoteSettingsSender = new SendRemoteSettingsSender(sender);
		RemoteSettings remoteSettings = new RemoteSettings(myPlayerNameTextfield.getText());
		remoteSettingsSender.sendMessage(remoteSettings);
	}

	@FXML
	public void onButtonStop() {
		SimpleNetworkSender networkSender = createNetworkSender();
		StopGameSender sender = new StopGameSender(networkSender);
		sender.sendMessage("");
	}

	@FXML
	public void onButtonKillPlayer() {
		PlayerIsDead playerIsDeadMessage = new PlayerIsDead(Integer.valueOf(killPlayerIdTextfield.getText()));
		new PlayerIsDeadSender(createNetworkSender()).sendMessage(playerIsDeadMessage);
	}

	private SimpleNetworkSender createNetworkSender() {
		return new SimpleNetworkSender(MessageParserFactory.create(), socketToServer);
	}

	@FXML
	public void onButtonSendSpawnpoint() {
		Double x = Double.valueOf(spawnpointX.getText());
		Double y = Double.valueOf(spawnpointY.getText());
		SpawnPoint spawnpoint = new SpawnPoint((long) (ModelConstants.STANDARD_WORLD_SIZE * x), (long) (ModelConstants.STANDARD_WORLD_SIZE * y));
		SpawnPointMessage message = new SpawnPointMessage(spawnpoint, Integer.valueOf(sendSpawnpointPlayerId.getText()));
		new SpawnPointSender(createNetworkSender()).sendMessage(message);
	}

	@FXML
	public void onButtonPlayerScore() {
		PlayerScoreMessage message = new PlayerScoreMessage(Integer.valueOf(playerScoreIdTextfield.getText()), Integer.valueOf(playerScoreTextfield.getText()));
		new PlayerScoreSender(createNetworkSender()).sendMessage(message);
	}
}
