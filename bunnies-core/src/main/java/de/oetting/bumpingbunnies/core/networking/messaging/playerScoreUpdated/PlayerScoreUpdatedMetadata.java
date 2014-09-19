package de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated;

import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public class PlayerScoreUpdatedMetadata extends MessageMetadata<PlayerScoreMessage> {

	public PlayerScoreUpdatedMetadata() {
		super(MessageId.PLAYER_SCORE_UPDATE, PlayerScoreMessage.class);
	}

}
