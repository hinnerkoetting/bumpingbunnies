package de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class PlayerIsRevivedMetadata extends MessageMetadata<Integer> {

	public PlayerIsRevivedMetadata() {
		super(MessageId.PLAYER_IS_REVIVED, Integer.class);
	}

}
