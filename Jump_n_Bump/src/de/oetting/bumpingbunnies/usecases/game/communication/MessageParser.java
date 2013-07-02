package de.oetting.bumpingbunnies.usecases.game.communication;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class MessageParser {

	private final Gson gson;

	public MessageParser(Gson gson) {
		super();
		this.gson = gson;
	}

	public <T> T parseMessage(String msg, Class<T> clazz) {
		return this.gson.fromJson(msg, clazz);
	}

	public String encodeMessage(JsonWrapper wrapper) {
		return this.gson.toJson(wrapper);
	}
}
