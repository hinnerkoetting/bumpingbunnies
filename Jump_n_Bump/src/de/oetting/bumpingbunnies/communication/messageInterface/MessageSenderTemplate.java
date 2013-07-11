package de.oetting.bumpingbunnies.communication.messageInterface;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public abstract class MessageSenderTemplate<T> implements MessageInterface<T> {

	private final SimpleNetworkSender networkSender;
	private final MessageParser parser;
	private final int messageId;

	public MessageSenderTemplate(SimpleNetworkSender networkSender, int messageId) {
		this(networkSender, new MessageParser(new Gson()), messageId);
	}

	public MessageSenderTemplate(SimpleNetworkSender networkSender, MessageParser parser, int messageId) {
		super();
		this.networkSender = networkSender;
		this.parser = parser;
		this.messageId = messageId;
	}

	public void sendMessage(T message) {
		String encoded = this.parser.encodeMessage(message);
		this.networkSender.sendMessage(new JsonWrapper(this.messageId, encoded));
	}

}
