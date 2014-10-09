package de.oetting.bumpingbunnies.core.networking.server;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.RemoteSettingsReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.sender.OtherPlayerClientIdSender;
import de.oetting.bumpingbunnies.core.networking.sender.SendClientPlayerIdSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;

public class ToClientConnector {

	private static final Logger LOGGER = LoggerFactory.getLogger(ToClientConnector.class);
	private final AcceptsClientConnections clientConnectionsAcceptor;
	private final NetworkReceiver networkReceiver;
	private final SocketStorage sockets;
	private MySocket socket;

	public ToClientConnector(AcceptsClientConnections roomActivity, NetworkReceiver networkReceiver, SocketStorage sockets) {
		super();
		this.clientConnectionsAcceptor = roomActivity;
		this.networkReceiver = networkReceiver;
		this.sockets = sockets;
	}

	public void onConnectToClient(MySocket socket) {
		this.socket = socket;
		addObserver();
		this.networkReceiver.start();
	}

	private void addObserver() {
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver.getGameDispatcher();
		new RemoteSettingsReceiver(gameDispatcher, this);
	}

	public void onReceiveRemotePlayersettings(RemoteSettings message) {
		LOGGER.info("Received remote Settings from client %s", message);
		networkReceiver.cancel();
		manageConnectedClient(socket, message);
	}

	private void manageConnectedClient(MySocket socket, RemoteSettings settings) {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(socket);
		notifyAboutExistingPlayers(networkSender);
		int nextPlayerId = getNextPlayerId();
		PlayerProperties playerProperties = new PlayerProperties(nextPlayerId, settings.getRemotePlayerName());
		notifyExistingClients(playerProperties);
		sendClientPlayer(networkSender, nextPlayerId);
		int socketIndex = this.sockets.addSocket(socket);
		this.clientConnectionsAcceptor.addPlayerEntry(socket, playerProperties, socketIndex);
	}

	private void notifyExistingClients(PlayerProperties playerProperties) {
		LOGGER.info("Notifying existing clients about new player with id %d", playerProperties.getPlayerId());
		List<MySocket> allOtherPlayers = this.sockets.getAllSockets();
		for (MySocket otherPlayer : allOtherPlayers) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(otherPlayer);
			new OtherPlayerClientIdSender(networkSender).sendMessage(playerProperties);
		}
	}

	private void notifyAboutExistingPlayers(SimpleNetworkSender networkSender) {
		LOGGER.info("Notifying new connected player about all existing players.");
		for (PlayerProperties otherPlayer : this.clientConnectionsAcceptor.getAllPlayersProperties()) {
			informClientAboutPlayer(otherPlayer, networkSender);
		}
	}

	private void informClientAboutPlayer(PlayerProperties player, SimpleNetworkSender networkSender) {
		new OtherPlayerClientIdSender(networkSender).sendMessage(player);
	}

	private void sendClientPlayer(SimpleNetworkSender networkSender, int playerId) {
		LOGGER.info("Notifying new Player about his id %d", playerId);
		new SendClientPlayerIdSender(networkSender).sendMessage(playerId);
	}

	private int getNextPlayerId() {
		return this.clientConnectionsAcceptor.getNextPlayerId();
	}

	public void cancel() {
		this.networkReceiver.cancel();
	}

}
