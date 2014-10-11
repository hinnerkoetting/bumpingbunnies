package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

public abstract class DefaultNetworkListener<T> implements NetworkListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNetworkListener.class);
	private final Class<T> clazz;
	private final MessageParser parser;

	public DefaultNetworkListener(Class<T> clazz) {
		this.clazz = clazz;
		this.parser = MessageParserFactory.create();
	}

	@Override
	public void newMessage(JsonWrapper wrapper) {
		String message = wrapper.getMessage();
		LOGGER.debug("received message %s", message);
		receiveMessage(this.parser.parseMessage(message, this.clazz));
	}

	public abstract void receiveMessage(T object);
}
