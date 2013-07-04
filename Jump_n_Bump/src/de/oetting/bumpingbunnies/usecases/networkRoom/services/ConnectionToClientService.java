package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomEntry;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class ConnectionToClientService {

	private RoomActivity roomActivity;
	private MessageParser parser;

	public ConnectionToClientService(RoomActivity roomActivity) {
		super();
		this.roomActivity = roomActivity;
		this.parser = MessageParserFactory.create();
	}

	public void onConnectToClient(MySocket socket) {
		manageConnectedClient(socket);
	}

	private void manageConnectedClient(MySocket socket) {
		SimpleNetworkSender networkSender = SimpleNetworkSenderFactory
				.createNetworkSender(socket);
		notifyAboutExistingPlayers(socket, networkSender);
		int nextPlayerId = getNextPlayerId();
		sendClientPlayer(networkSender, nextPlayerId);
		int socketIndex = SocketStorage.getSingleton().addSocket(socket);
		this.roomActivity.addPlayerEntry(socket, nextPlayerId, socketIndex);
	}

	private void notifyAboutExistingPlayers(MySocket socket,
			SimpleNetworkSender networkSender) {

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
		JsonWrapper clientPlayer = createJsonPlayerId(playerId);
		networkSender.sendMessage(clientPlayer);
	}

	private JsonWrapper createPlayerInfoMessage(RoomEntry entry) {
		return new JsonWrapper(MessageIds.SEND_OTHER_PLAYER_ID,
				this.parser.encodeMessage(Integer.valueOf(entry
						.getPlayerConfiguration().getPlayerId())));
	}

	private JsonWrapper createJsonPlayerId(int playerId) {
		return new JsonWrapper(MessageIds.SEND_CLIENT_PLAYER_ID,
				this.parser.encodeMessage(playerId));
	}

	private int getNextPlayerId() {
		return this.roomActivity.getNextPlayerId();
	}

}
