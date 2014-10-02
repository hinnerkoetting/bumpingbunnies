package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;

public class SimpleNetworkSenderFactory {

	public static SimpleNetworkSender createNetworkSender(MySocket socket) {
		return new SimpleNetworkSender(MessageParserFactory.create(), socket);
	}
}
