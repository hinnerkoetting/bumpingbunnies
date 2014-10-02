package de.oetting.bumpingbunnies.core.networking.communication.messageInterface;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.MessageParser;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public abstract class MessageSenderTemplate<T> implements MessageInterface<T> {

	private final NetworkSender networkSender;
	private final MessageParser parser;
	private final MessageId messageId;

	public MessageSenderTemplate(NetworkSender networkSender, MessageMetadata<T> msg) {
		this(networkSender, msg.getId());
	}

	public MessageSenderTemplate(NetworkSender networkSender, MessageId messageId) {
		this(networkSender, new MessageParser(new Gson()), messageId);
	}

	public MessageSenderTemplate(NetworkSender networkSender, MessageParser parser, MessageId messageId) {
		super();
		this.networkSender = networkSender;
		this.parser = parser;
		this.messageId = messageId;
	}

	public void sendMessage(T message) {
		String encoded = this.parser.encodeMessage(message);
		this.networkSender.sendMessage(JsonWrapper.create(this.messageId, encoded));
	}

}
