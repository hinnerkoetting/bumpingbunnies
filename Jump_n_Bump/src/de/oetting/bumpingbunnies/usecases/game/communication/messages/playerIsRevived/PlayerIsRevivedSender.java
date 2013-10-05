package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;

public class PlayerIsRevivedSender extends MessageSenderTemplate<Integer> {

	public PlayerIsRevivedSender(ThreadedNetworkSender networkSender) {
		super(networkSender, new PlayerIsRevivedMetadata());
	}

}
