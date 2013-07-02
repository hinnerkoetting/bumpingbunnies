package de.oetting.bumpingbunnies.usecases.game.communication;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;

public class MessageParser {

	private static final MyLog LOGGER = Logger.getLogger(MessageParser.class);
	private final Gson gson;

	public MessageParser(Gson gson) {
		super();
		this.gson = gson;
	}

	public <T> T parseMessage(String msg, Class<T> clazz) {
		try {
			return this.gson.fromJson(msg, clazz);
		} catch (JsonSyntaxException e) {
			LOGGER.error(
					"Exception while parsind json message. Input was %s. Target class was %s",
					msg, clazz.getSimpleName());
			throw e;
		}
	}

	public String encodeMessage(Object message) {
		return this.gson.toJson(message);
	}
}
