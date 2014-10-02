package de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;

public class PlayerScoreSender extends MessageSenderTemplate<PlayerScoreMessage> {

	public PlayerScoreSender(NetworkSender networkSender) {
		super(networkSender, new PlayerScoreUpdatedMetadata());
	}

}
