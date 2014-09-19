package de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived;

import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public class PlayerIsRevivedMetadata extends MessageMetadata<Integer> {

	public PlayerIsRevivedMetadata() {
		super(MessageId.PLAYER_IS_REVIVED, Integer.class);
	}

}
