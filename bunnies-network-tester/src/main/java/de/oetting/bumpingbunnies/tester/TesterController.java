package de.oetting.bumpingbunnies.tester;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
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
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class TesterController implements Initializable, OnBroadcastReceived, DisplaysConnectedServers, ConnectsToServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TesterController.class);

	@FXML
	private TableView<Host> broadcastTable;
	@FXML
	private TableView<RoomEntry> playersTable;

	private ListenForBroadcastsThread listenForBroadcasts;

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
		return new LocalPlayerSettings("");
	}

	public void launchGame(GeneralSettings generalSettingsFromNetwork, boolean asHost) {
	}

}
