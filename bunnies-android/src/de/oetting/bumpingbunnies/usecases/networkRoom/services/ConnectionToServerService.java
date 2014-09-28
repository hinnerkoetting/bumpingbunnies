package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.generalSettings.GameSettingsReceiver;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.otherPlayerId.OtherPlayerClientIdReceiver;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId.SendClientPlayerIdReceiver;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendLocalSettings.SendLocalSettingsSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame.StartGameReceiver;

public class ConnectionToServerService implements ConnectionToServer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionToServerService.class);
	private final NetworkReceiver networkReceiver;
	private GeneralSettings generalSettingsFromNetwork;
	private RoomActivity roomActivity;
	private final MySocket socket;

	public ConnectionToServerService(MySocket socket, RoomActivity roomActivity) {
		this.socket = socket;
		this.roomActivity = roomActivity;
		this.networkReceiver = NetworkReceiverDispatcherThreadFactory
				.createRoomNetworkReceiver(socket);
	}

	@Override
	public void onConnectionToServer() {
		SocketStorage.getSingleton().addSocket(this.socket);
		sendMyPlayerName();
		addObserver();
		this.networkReceiver.start();
	}

	private void sendMyPlayerName() {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
				.createNetworkSender(this.socket);
		LocalPlayerSettings localPlayerSettings = this.roomActivity
				.createLocalPlayerSettingsFromIntent();
		new SendLocalSettingsSender(networkSender).sendMessage(localPlayerSettings);
	}

	private void addObserver() {
		LOGGER.info("registering observers to receive messages from server");
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver
				.getGameDispatcher();
		new StartGameReceiver(gameDispatcher, this);
		new GameSettingsReceiver(gameDispatcher, this);
		new SendClientPlayerIdReceiver(gameDispatcher, this);
		new OtherPlayerClientIdReceiver(gameDispatcher, this);
	}

	public void addOtherPlayer(PlayerProperties object) {
		addPlayerEntry(ConnectionToServerService.this.socket,
				object, 0);
	}

	public void onReceiveGameSettings(GeneralSettings message) {
		ConnectionToServerService.this.generalSettingsFromNetwork = message;
	}

	public void onReceiveStartGame() {
		this.networkReceiver.cancel();
		launchGame();
	}

	protected void addPlayerEntry(MySocket serverSocket,
			PlayerProperties properties, int socketIndex) {
		this.roomActivity.addPlayerEntry(serverSocket, properties, socketIndex);
	}

	public void addMyPlayerRoomEntry(int myPlayerId) {
		this.roomActivity.addMyPlayerRoomEntry(myPlayerId);
	}

	private void launchGame() {
		if (this.generalSettingsFromNetwork == null) {
			throw new GeneralSettingsWereNotYetReceived();
		}
		this.roomActivity.launchGame(this.generalSettingsFromNetwork, false);
	}

	@Override
	public void cancel() {
		this.networkReceiver.cancel();
	}

	public static class GeneralSettingsWereNotYetReceived extends RuntimeException {
	}
}