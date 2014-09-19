package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;

public class MessageParserFactory {

	public static MessageParser create() {
		return new MessageParser(new Gson());
	}
}
