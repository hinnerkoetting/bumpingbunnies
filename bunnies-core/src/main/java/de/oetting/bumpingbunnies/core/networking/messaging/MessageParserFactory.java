package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MessageParser;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;

public class MessageParserFactory {

	public static MessageParser create() {
		return new MessageParser(new GsonFactory().create());
	}
}
