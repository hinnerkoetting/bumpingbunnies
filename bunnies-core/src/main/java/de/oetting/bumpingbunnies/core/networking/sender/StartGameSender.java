package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.messaging.StartGameMessage;

public class StartGameSender extends MessageSenderTemplate<String> {

	public StartGameSender(SimpleNetworkSender networkSender) {
		super(networkSender, new StartGameMessage());
	}

}
