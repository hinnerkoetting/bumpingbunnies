package de.oetting.bumpingbunnies.core.networking.server;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.SendLocalSettingsReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.sender.OtherPlayerClientIdSender;
import de.oetting.bumpingbunnies.core.networking.sender.SendClientPlayerIdSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public class ConnectionToClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionToClientService.class);
	private final AcceptsClientConnections roomActivity;
	private final NetworkReceiver networkReceiver;
	private final SocketStorage sockets;
	private MySocket socket;

	public ConnectionToClientService(AcceptsClientConnections roomActivity, NetworkReceiver networkReceiver, SocketStorage sockets) {
		super();
		this.roomActivity = roomActivity;
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
		new SendLocalSettingsReceiver(gameDispatcher, this);
	}

	public void onReceiveLocalPlayersettings(LocalPlayerSettings message) {
		ConnectionToClientService.this.networkReceiver.cancel();
		manageConnectedClient(ConnectionToClientService.this.socket, message.getPlayerName());
	}

	private void manageConnectedClient(MySocket socket, String playerName) {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(socket);
		notifyAboutExistingPlayers(networkSender);

		int nextPlayerId = getNextPlayerId();
		PlayerProperties playerProperties = new PlayerProperties(nextPlayerId, playerName);
		notifyExistingClients(playerProperties);
		sendClientPlayer(networkSender, nextPlayerId);
		int socketIndex = this.sockets.addSocket(socket);
		this.roomActivity.addPlayerEntry(socket, playerProperties, socketIndex);
	}

	private void notifyExistingClients(PlayerProperties playerProperties) {
		LOGGER.info("Notifying existing clients about new player with id %d", playerProperties.getPlayerId());
		List<MySocket> allOtherPlayers = this.roomActivity.getAllOtherSockets();
		for (MySocket otherPlayer : allOtherPlayers) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(otherPlayer);
			new OtherPlayerClientIdSender(networkSender).sendMessage(playerProperties);
		}
	}

	private void notifyAboutExistingPlayers(SimpleNetworkSender networkSender) {
		LOGGER.info("Notifying new Player all existing players");
		for (PlayerProperties otherPlayer : this.roomActivity.getAllPlayersProperties()) {
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
		return this.roomActivity.getNextPlayerId();
	}

	public void cancel() {
		this.networkReceiver.cancel();
	}

}
