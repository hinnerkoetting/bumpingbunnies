package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.AcceptsClientConnections;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.otherPlayerId.OtherPlayerClientIdSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId.SendClientPlayerIdSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendLocalSettings.SendLocalSettingsReceiver;

public class ConnectionToClientService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionToClientService.class);
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

	private void sendClientPlayer(SimpleNetworkSender networkSender,
			int playerId) {
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
