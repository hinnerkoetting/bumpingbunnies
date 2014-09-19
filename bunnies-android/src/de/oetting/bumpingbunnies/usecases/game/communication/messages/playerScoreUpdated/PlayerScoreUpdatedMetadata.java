package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageMetadata;

public class PlayerScoreUpdatedMetadata extends MessageMetadata<PlayerScoreMessage> {

	public PlayerScoreUpdatedMetadata() {
		super(MessageId.PLAYER_SCORE_UPDATE, PlayerScoreMessage.class);
	}

}
