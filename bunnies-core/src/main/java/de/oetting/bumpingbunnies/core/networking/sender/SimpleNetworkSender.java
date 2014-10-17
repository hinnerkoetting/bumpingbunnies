package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.network.MessageParser;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.AbstractSocket.WriteFailed;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SimpleNetworkSender implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleNetworkSender.class);
	private final MySocket socket;
	private final MessageParser parser;
	private final PlayerDisconnectedCallback disconnectCallback;

	public SimpleNetworkSender(MessageParser parser, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		this.parser = parser;
		this.socket = socket;
		this.disconnectCallback = disconnectCallback;
	}

	@Override
	public void sendMessage(JsonWrapper message) {
		LOGGER.debug("sending message %s", message.getMessage());
		String json = this.parser.encodeMessage(message);
		try {
			this.socket.sendMessage(json);
		} catch (WriteFailed e) {
			disconnectCallback.playerDisconnected(socket.getOwner());
			SocketStorage.getSingleton().removeSocket(socket);
		}
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		String json = this.parser.encodeMessage(message);
		sendMessage(JsonWrapper.create(id, json));
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return this.socket.equals(socket);
	}

	@Override
	public boolean isConnectionToPlayer(ConnectionIdentifier opponent) {
		return this.socket.getOwner().equals(opponent);
	}

	@Override
	public void cancel() {
	}

	@Override
	public void start() {
	}

}
