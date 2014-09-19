package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;

public class PlayerScoreSender extends MessageSenderTemplate<PlayerScoreMessage> {

	public PlayerScoreSender(NetworkSender networkSender) {
		super(networkSender, new PlayerScoreUpdatedMetadata());
	}

}
