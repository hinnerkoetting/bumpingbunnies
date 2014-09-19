package de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;

public class PlayerIsRevivedSender extends MessageSenderTemplate<Integer> {

	public PlayerIsRevivedSender(NetworkSender networkSender) {
		super(networkSender, new PlayerIsRevivedMetadata());
	}

}
