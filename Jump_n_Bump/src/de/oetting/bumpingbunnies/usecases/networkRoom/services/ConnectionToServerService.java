package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.DefaultNetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

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

		addObserver();
		this.networkReceiver.start();
	}

	private void addObserver() {
		LOGGER.info("registering observers to receive messages from server");
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver
				.getGameDispatcher();
		gameDispatcher.addObserver(MessageIds.START_GAME_ID,
				new DefaultNetworkListener<Object>(Object.class) {

					@Override
					public void receiveMessage(Object message) {
						ConnectionToServerService.this.networkReceiver.cancel();
						launchGame();
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_CONFIGURATION_ID,
				new DefaultNetworkListener<GeneralSettings>(
						GeneralSettings.class) {

					@Override
					public void receiveMessage(GeneralSettings message) {
						ConnectionToServerService.this.generalSettingsFromNetwork = message;
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_CLIENT_PLAYER_ID,
				new DefaultNetworkListener<Integer>(Integer.class) {

					@Override
					public void receiveMessage(Integer object) {
						addMyPlayerRoomEntry(object);
					}
				});
		gameDispatcher.addObserver(MessageIds.SEND_OTHER_PLAYER_ID,
				new DefaultNetworkListener<PlayerProperties>(
						PlayerProperties.class) {

					@Override
					public void receiveMessage(PlayerProperties object) {
						addPlayerEntry(ConnectionToServerService.this.socket,
								object, 0);
					}
				});
	}

	protected void addPlayerEntry(MySocket serverSocket,
			PlayerProperties properties, int socketIndex) {
		this.roomActivity.addPlayerEntry(serverSocket, properties, socketIndex);
	}

	protected void addMyPlayerRoomEntry(int myPlayerId) {
		this.roomActivity.addMyPlayerRoomEntry(myPlayerId);
	}

	private void launchGame() {
		this.roomActivity.launchGame(this.generalSettingsFromNetwork);
	}

	@Override
	public void cancel() {
		this.networkReceiver.cancel();
	}
}
