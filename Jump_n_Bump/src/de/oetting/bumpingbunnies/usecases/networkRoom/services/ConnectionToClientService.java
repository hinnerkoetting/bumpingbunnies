package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomEntry;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId.SendClientPlayerIdSender;
import de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendLocalSettings.SendLocalSettingsReceiver;

public class ConnectionToClientService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionToClientService.class);
	private final RoomActivity roomActivity;
	private final MessageParser parser;
	private final NetworkReceiver networkReceiver;
	private MySocket socket;

	public ConnectionToClientService(RoomActivity roomActivity,
			NetworkReceiver networkReceiver) {
		super();
		this.roomActivity = roomActivity;
		this.networkReceiver = networkReceiver;
		this.parser = MessageParserFactory.create();
	}

	public void onConnectToClient(MySocket socket) {
		this.socket = socket;
		addObserver();
		this.networkReceiver.start();
	}

	private void addObserver() {
		NetworkToGameDispatcher gameDispatcher = this.networkReceiver
				.getGameDispatcher();
		new SendLocalSettingsReceiver(gameDispatcher, this);
	}

	public void onReceiveLocalPlayersettings(LocalPlayersettings message) {
		ConnectionToClientService.this.networkReceiver.cancel();
		manageConnectedClient(
				ConnectionToClientService.this.socket,
				message.getPlayerName());
	}

	private void notifyExistingClients(PlayerProperties playerProperties) {
		LOGGER.info("Notifying existing clients about new player with id %d",
				playerProperties.getPlayerId());
		List<RoomEntry> allOtherPlayers = this.roomActivity
				.getAllOtherPlayers();
		for (RoomEntry otherPlayer : allOtherPlayers) {
			SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
					.createNetworkSender(otherPlayer.getSocket());
			JsonWrapper message = createPlayerInfoMessage(playerProperties);
			networkSender.sendMessage(message);
		}
	}

	private void manageConnectedClient(MySocket socket, String playerName) {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
				.createNetworkSender(socket);
		notifyAboutExistingPlayers(socket, networkSender);

		int nextPlayerId = getNextPlayerId();
		PlayerProperties playerProperties = new PlayerProperties(nextPlayerId,
				playerName);
		notifyExistingClients(playerProperties);
		sendClientPlayer(networkSender, nextPlayerId);
		int socketIndex = SocketStorage.getSingleton().addSocket(socket);
		this.roomActivity.addPlayerEntry(socket, playerProperties, socketIndex);
	}

	private void notifyAboutExistingPlayers(MySocket socket,
			SimpleNetworkSender networkSender) {
		LOGGER.info("Notifying new Player all existing players");
		for (RoomEntry otherPlayer : this.roomActivity.getAllOtherPlayers()) {
			informClientAboutPlayer(otherPlayer, networkSender);
		}
		informClientAboutPlayer(this.roomActivity.getMyself(), networkSender);

	}

	private void informClientAboutPlayer(RoomEntry player,
			SimpleNetworkSender networkSender) {
		JsonWrapper message = createPlayerInfoMessage(player);
		networkSender.sendMessage(message);
	}

	private void sendClientPlayer(SimpleNetworkSender networkSender,
			int playerId) {
		LOGGER.info("Notifying new Player about his id %d", playerId);
		new SendClientPlayerIdSender(networkSender).sendMessage(playerId);
	}

	private JsonWrapper createPlayerInfoMessage(RoomEntry entry) {
		return createPlayerInfoMessage(entry.getPlayerConfiguration());
	}

	private JsonWrapper createPlayerInfoMessage(
			PlayerProperties playerProperties) {
		return new JsonWrapper(MessageIds.SEND_OTHER_PLAYER_ID,
				this.parser.encodeMessage(playerProperties));
	}

	private int getNextPlayerId() {
		return this.roomActivity.getNextPlayerId();
	}

	public void cancel() {
		this.networkReceiver.cancel();
	}

}
