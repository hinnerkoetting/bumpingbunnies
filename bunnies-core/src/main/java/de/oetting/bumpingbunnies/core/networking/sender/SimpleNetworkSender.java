package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.network.MessageParser;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SimpleNetworkSender implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleNetworkSender.class);
	private final MySocket socket;
	private final MessageParser parser;

	public SimpleNetworkSender(MessageParser parser, MySocket socket) {
		super();
		this.parser = parser;
		this.socket = socket;
	}

	@Override
	public void sendMessage(JsonWrapper message) {
		try {
			LOGGER.debug("sending message %s", message.getMessage());
			String json = this.parser.encodeMessage(message);
			this.socket.sendMessage(json);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		String json = this.parser.encodeMessage(message);
		sendMessage(JsonWrapper.create(id, json));
	}

	@Override
	public void sendMessageFast(MessageId id, Object message) {
		sendMessage(id, message);
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return this.socket.equals(socket);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.socket.getOwner().equals(opponent);
	}

	@Override
	public void cancel() {
	}

}
