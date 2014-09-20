package de.oetting.bumpingbunnies.core.networking.messaging;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.networking.MessageParser;

public class MessageParserFactory {

	public static MessageParser create() {
		return new MessageParser(new Gson());
	}
}
