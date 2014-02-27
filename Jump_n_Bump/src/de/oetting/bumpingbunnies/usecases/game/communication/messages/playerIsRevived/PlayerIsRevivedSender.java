package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;

public class PlayerIsRevivedSender extends MessageSenderTemplate<Integer> {

	public PlayerIsRevivedSender(NetworkSender networkSender) {
		super(networkSender, new PlayerIsRevivedMetadata());
	}

}
