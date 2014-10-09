package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.GameSettingsReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OtherPlayerClientIdReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.receive.SendClientPlayerIdReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.StartGameReceiver;
import de.oetting.bumpingbunnies.core.networking.sender.SendRemoteSettingsSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public class SetupConnectionWithServer implements ConnectionToServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SetupConnectionWithServer.class);
	private final NetworkReceiver networkReceiver;
	private ServerSettings generalSettingsFromNetwork;
	private DisplaysConnectedServers displaysConnectedPlayers;
	private final MySocket socket;
	private final PlayerDisconnectedCallback disconnectCallback;

	public SetupConnectionWithServer(MySocket socket, DisplaysConnectedServers displaysConnectedPlayers, PlayerDisconnectedCallback playerDisconnected,
			PlayerDisconnectedCallback disconnectCallback) {
		this.socket = socket;
		this.displaysConnectedPlayers = displaysConnectedPlayers;
		this.disconnectCallback = disconnectCallback;
		this.networkReceiver = NetworkReceiverDispatcherThreadFactory.createRoomNetworkReceiver(socket, playerDisconnected);
	}

	@Override
	public void onConnectionToServer() {
		SocketStorage.getSingleton().addSocket(this.socket);
		sendMySettings();
		addObserver();
		this.networkReceiver.start();
	}

	private void sendMySettings() {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(this.socket, disconnectCallback);
		LocalPlayerSettings localPlayerSettings = this.displaysConnectedPlayers.createLocalPlayerSettings();
		RemoteSettings remoteSettings = new RemoteSettings(localPlayerSettings.getPlayerName());
		new SendRemoteSettingsSender(networkSender).sendMessage(remoteSettings);
	}

	private void addObserver() {
		LOGGER.info("registering observers to receive messages from server");
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver.getGameDispatcher();
		new StartGameReceiver(gameDispatcher, this);
		new GameSettingsReceiver(gameDispatcher, this);
		new SendClientPlayerIdReceiver(gameDispatcher, this);
		new OtherPlayerClientIdReceiver(gameDispatcher, this);
	}

	public void addOtherPlayer(PlayerProperties object) {
		addPlayerEntry(SetupConnectionWithServer.this.socket, object, 0);
	}

	public void onReceiveGameSettings(ServerSettings message) {
		this.generalSettingsFromNetwork = message;
	}

	public void onReceiveStartGame() {
		this.networkReceiver.cancel();
		launchGame();
	}

	protected void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex) {
		this.displaysConnectedPlayers.addPlayerEntry(serverSocket, properties, socketIndex);
	}

	public void addMyPlayerRoomEntry(int myPlayerId) {
		this.displaysConnectedPlayers.addMyPlayerRoomEntry(myPlayerId);
	}

	private void launchGame() {
		if (this.generalSettingsFromNetwork == null) {
			throw new GeneralSettingsWereNotYetReceived();
		}
		this.displaysConnectedPlayers.launchGame(this.generalSettingsFromNetwork, false);
	}

	@Override
	public void cancel() {
		this.networkReceiver.cancel();
	}

	public static class GeneralSettingsWereNotYetReceived extends RuntimeException {
	}
}