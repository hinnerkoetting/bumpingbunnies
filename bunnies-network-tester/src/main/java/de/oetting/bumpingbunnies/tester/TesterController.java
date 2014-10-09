package de.oetting.bumpingbunnies.tester;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.BytePerSecondMeasurer;
import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.WlanDevice;
import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.network.room.DetailRoomEntry;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.core.networking.SinglePlayerRoomEntry;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerEstablisher;
import de.oetting.bumpingbunnies.core.networking.client.DisplaysConnectedServers;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadSender;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreSender;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameSender;
import de.oetting.bumpingbunnies.core.networking.receive.EasyNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SendRemoteSettingsSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class TesterController implements Initializable, OnBroadcastReceived, DisplaysConnectedServers, ConnectsToServer, PlayerDisconnectedCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(TesterController.class);

	@FXML
	private TableView<Host> broadcastTable;
	@FXML
	private TableView<DetailRoomEntry> playersTable;
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
	@FXML
	private TextField playerScoreTextfield;
	@FXML
	private TextField playerStateIdTextfield;
	@FXML
	private TextField playerX;
	@FXML
	private TextField playerY;
	@FXML
	private TextField movementX;
	@FXML
	private TextField movementY;
	@FXML
	private TextField accelerationX;
	@FXML
	private TextField accelerationY;
	@FXML
	private TextField scoreTextfield;
	@FXML
	private CheckBox facingLeftCheckbox;
	@FXML
	private CheckBox jumpingCheckbox;
	@FXML
	private CheckBox deadCheckbox;
	@FXML
	private TextField playerStateCounterTextfield;
	@FXML
	private ComboBox<String> playerStateMovement;
	@FXML
	private TextField incmingBytesPerSecondTextfield;

	private ListenForBroadcastsThread listenForBroadcasts;
	private MySocket tcpSocketToServer;
	private MySocket udpSocketToServer;

	private SetupConnectionWithServer connectedToServerService;

	private BytePerSecondMeasurer bpsMeasurer = new BytePerSecondMeasurer(System.currentTimeMillis());

	public void initialize(URL location, ResourceBundle resources) {
		listenForBroadcasts = ListenforBroadCastsThreadFactory.create(this);
		listenForBroadcasts.start();
		updateBytesPerSecond();
	}

	private void updateBytesPerSecond() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					int bytesPersecond = bpsMeasurer.getBytesPerSecond(System.currentTimeMillis());
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							incmingBytesPerSecondTextfield.setText(Integer.toString(bytesPersecond));
						}
					});
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// ignore
					}
				}
			}
		}).start();
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
		ConnectionToServerEstablisher connectToServerThread = new ConnectionToServerEstablisher(factory.createClientSocket(wlanDevice), this);
		connectToServerThread.start();
	}

	public void connectionNotSuccesful(String message) {
		Platform.exit();
	}

	public void connectToServerSuccesfull(MySocket mmSocket) {
		this.tcpSocketToServer = mmSocket;
		udpSocketToServer = UdpSocketFactory.singleton().create((TCPSocket) tcpSocketToServer, tcpSocketToServer.getOwner());
		LOGGER.info("Connected to server %s", mmSocket);
		connectedToServerService = new SetupConnectionWithServer(mmSocket, this, this);
		connectedToServerService.onConnectionToServer();
	}

	public void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex) {
		RoomEntry entry = new RoomEntry(properties, serverSocket, socketIndex);
		Player player = new Player(properties.getPlayerId(), properties.getPlayerName(), -1, Opponent.createOpponent("TEMP", OpponentType.LOCAL_PLAYER));
		playersTable.getItems().add(new DetailRoomEntry(entry, player));
	}

	public void addMyPlayerRoomEntry(int myPlayerId) {
		LocalPlayerSettings settings = createLocalPlayerSettings();
		PlayerProperties singlePlayerProperties = new PlayerProperties(myPlayerId, settings.getPlayerName());
		Player player = new Player(myPlayerId, settings.getPlayerName(), -1, Opponent.createMyPlayer(singlePlayerProperties.getPlayerName()));
		playersTable.getItems().add(new DetailRoomEntry(new SinglePlayerRoomEntry(singlePlayerProperties), player));
	}

	public LocalPlayerSettings createLocalPlayerSettings() {
		return new LocalPlayerSettings(myPlayerNameTextfield.getText());
	}

	public void launchGame(GeneralSettings generalSettingsFromNetwork, boolean asHost) {
		connectedToServerService.cancel();
		EasyNetworkToGameDispatcher tcpNetworkToGameDispatcher = new EasyNetworkToGameDispatcher(this);
		EasyNetworkToGameDispatcher updNetworkToGameDispatcher = new EasyNetworkToGameDispatcher(this);
		NetworkReceiveThread receiverTcpThread = new NetworkReceiveThread(new Gson(), tcpNetworkToGameDispatcher, tcpSocketToServer);
		NetworkReceiveThread receiverUdpThread = new NetworkReceiveThread(new Gson(), updNetworkToGameDispatcher, udpSocketToServer);
		addTcpListeners(tcpNetworkToGameDispatcher);
		addUdpListeners(updNetworkToGameDispatcher);
		receiverTcpThread.start();
		receiverUdpThread.start();
	}

	private void addTcpListeners(EasyNetworkToGameDispatcher networkToGameDispatcher) {
		networkToGameDispatcher.addObserver(MessageId.PLAYER_SCORE_UPDATE, messageWrapper -> updateScore(messageWrapper));
		networkToGameDispatcher.addObserver(MessageId.PLAYER_IS_DEAD_MESSAGE, messageWrapper -> updateIsDead(messageWrapper));
		networkToGameDispatcher.addObserver(MessageId.SPAWN_POINT, messageWrapper -> updateSpawnpoint(messageWrapper));
		networkToGameDispatcher.addObserver(MessageId.PLAYER_IS_REVIVED, messageWrapper -> updateIsRevived(messageWrapper));
	}

	private void addUdpListeners(EasyNetworkToGameDispatcher networkToGameDispatcher) {
		networkToGameDispatcher.addObserver(MessageId.SEND_PLAYER_STATE, messageWrapper -> updatePlayerState(messageWrapper));
	}

	private void updatePlayerState(JsonWrapper messageWrapper) {
		bpsMeasurer.newMessage(messageWrapper.getMessage(), System.currentTimeMillis());
		PlayerStateMessage message = MessageParserFactory.create().parseMessage(messageWrapper.getMessage(), PlayerStateMessage.class);
		DetailRoomEntry roomEntry = findEntry(message.getPlayerState().getId());
		roomEntry.getPlayer().applyState(message.getPlayerState());
		updateTableItem(roomEntry);
	}

	private void updateScore(JsonWrapper messageWrapper) {
		bpsMeasurer.newMessage(messageWrapper.getMessage(), System.currentTimeMillis());
		PlayerScoreMessage message = MessageParserFactory.create().parseMessage(messageWrapper.getMessage(), PlayerScoreMessage.class);
		DetailRoomEntry roomEntry = findEntry(message.getPlayerId());
		roomEntry.getPlayer().setScore(message.getNewScore());

		updateTableItem(roomEntry);
	}

	private void updateIsDead(JsonWrapper wrapper) {
		bpsMeasurer.newMessage(wrapper.getMessage(), System.currentTimeMillis());
		PlayerIsDeadMessage message = MessageParserFactory.create().parseMessage(wrapper.getMessage(), PlayerIsDeadMessage.class);
		DetailRoomEntry roomEntry = findEntry(message.getPlayerId());
		roomEntry.getPlayer().setDead(true);
		updateTableItem(roomEntry);
	}

	private void updateSpawnpoint(JsonWrapper messageWrapper) {
		bpsMeasurer.newMessage(messageWrapper.getMessage(), System.currentTimeMillis());
		SpawnPointMessage spawnpoint = MessageParserFactory.create().parseMessage(messageWrapper.getMessage(), SpawnPointMessage.class);
		DetailRoomEntry roomEntry = findEntry(spawnpoint.getPlayerId());
		roomEntry.getPlayer().setCenterX(spawnpoint.getSpawnPoint().getX());
		roomEntry.getPlayer().setCenterY(spawnpoint.getSpawnPoint().getY());
		updateTableItem(roomEntry);
	}

	private void updateIsRevived(JsonWrapper messageWrapper) {
		bpsMeasurer.newMessage(messageWrapper.getMessage(), System.currentTimeMillis());
		Integer playerId = MessageParserFactory.create().parseMessage(messageWrapper.getMessage(), Integer.class);
		DetailRoomEntry roomEntry = findEntry(playerId);
		roomEntry.getPlayer().setDead(false);
		updateTableItem(roomEntry);
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
		PlayerIsDeadMessage playerIsDeadMessage = new PlayerIsDeadMessage(Integer.valueOf(killPlayerIdTextfield.getText()));
		new PlayerIsDeadSender(createNetworkSender()).sendMessage(playerIsDeadMessage);
	}

	private SimpleNetworkSender createNetworkSender() {
		return new SimpleNetworkSender(MessageParserFactory.create(), tcpSocketToServer);
	}

	private SimpleNetworkSender createUdpNetworkSender() {
		return new SimpleNetworkSender(MessageParserFactory.create(), udpSocketToServer);
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

	@FXML
	public void onSendStateButton() {
		PlayerState state = extractPlayerState();
		PlayerStateMessage message = new PlayerStateMessage(getCounterAndIncreate(), state);
		createUdpNetworkSender().sendMessage(MessageId.SEND_PLAYER_STATE, message);
	}

	private long getCounterAndIncreate() {
		long counter = Long.valueOf(playerStateCounterTextfield.getText());
		playerStateCounterTextfield.setText(Long.toString(counter + 1));
		return counter;
	}

	private PlayerState extractPlayerState() {
		PlayerState state = new PlayerState(Integer.valueOf(playerStateIdTextfield.getText()));
		state.setAccelerationX(readAccelerationX());
		state.setAccelerationY(readAccelerationY());
		state.setMovementX(readMovementX());
		state.setMovementY(readMovementY());
		state.setCenterX(readStateX());
		state.setCenterY(readStateY());
		state.setDead(deadCheckbox.isSelected());
		state.setFacingLeft(facingLeftCheckbox.isSelected());
		state.setJumpingButtonPressed(jumpingCheckbox.isSelected());
		state.setHorizontalMovementStatus(PlayerState.HorizontalMovementStatus.valueOf((String) playerStateMovement.getSelectionModel().getSelectedItem()));
		return state;
	}

	private Integer readAccelerationY() {
		Double y = Double.valueOf(accelerationY.getText());
		return (int) (y * ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
	}

	private Integer readAccelerationX() {
		Double x = Double.valueOf(accelerationX.getText());
		return (int) (x * ModelConstants.ACCELERATION_X_WALL);
	}

	private Integer readMovementY() {
		Double y = Double.valueOf(movementY.getText());
		return (int) (y * ModelConstants.MAX_X_MOVEMENT);
	}

	private Integer readMovementX() {
		Double x = Double.valueOf(movementX.getText());
		return (int) (x * ModelConstants.MAX_X_MOVEMENT);
	}

	private Long readStateX() {
		Double x = Double.valueOf(playerX.getText());
		return (long) (ModelConstants.STANDARD_WORLD_SIZE * x);
	}

	private Long readStateY() {
		Double y = Double.valueOf(playerY.getText());
		return (long) (ModelConstants.STANDARD_WORLD_SIZE * y);
	}

	public void playerDisconnected(Opponent opponent) {
		RoomEntry player = findPlayer(opponent);
		playersTable.getItems().remove(player);
	}

	private DetailRoomEntry findEntry(int playerId) {
		for (DetailRoomEntry entry : playersTable.getItems())
			if (entry.getPlayerId() == playerId)
				return entry;
		throw new IllegalArgumentException("Player not found " + playerId);
	}

	private RoomEntry findPlayer(Opponent opponent) {
		for (DetailRoomEntry entry : playersTable.getItems()) {
			if (entry.getEntry().getPlayerName().equals(opponent.getIdentifier())) {
				return entry.getEntry();
			}
		}
		throw new IllegalArgumentException("Player does not exist " + opponent);
	}

	private void updateTableItem(DetailRoomEntry roomEntry) {
		int indexOf = playersTable.getItems().indexOf(roomEntry);
		playersTable.getItems().set(indexOf, roomEntry);
	}
}
