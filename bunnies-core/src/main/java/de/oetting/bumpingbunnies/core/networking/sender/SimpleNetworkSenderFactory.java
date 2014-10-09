package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class SimpleNetworkSenderFactory {

	public static SimpleNetworkSender createNetworkSender(MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return new SimpleNetworkSender(MessageParserFactory.create(), socket, disconnectCallback);
	}
}
