package de.oetting.bumpingbunnies.usecases.game.communication;

import com.google.gson.Gson;

public abstract class DefaultNetworkListener<T> implements NetworkListener {

	private final Class<T> clazz;
	private final MessageParser parser;

	public DefaultNetworkListener(Class<T> clazz) {
		this.clazz = clazz;
		this.parser = new MessageParser(new Gson());
	}

	@Override
	public void newMessage(String message) {
		receiveMessage(this.parser.parseMessage(message, this.clazz));
	}

	public abstract void receiveMessage(T object);
}
