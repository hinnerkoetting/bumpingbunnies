package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageMetadata;

public class PlayerIsRevivedMetadata extends MessageMetadata<Integer> {

	public PlayerIsRevivedMetadata() {
		super(MessageId.PLAYER_IS_REVIVED, Integer.class);
	}

}
