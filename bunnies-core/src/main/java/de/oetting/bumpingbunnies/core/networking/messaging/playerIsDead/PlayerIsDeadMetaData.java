package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public class PlayerIsDeadMetaData extends MessageMetadata<PlayerIsDead> {

	public PlayerIsDeadMetaData() {
		super(MessageId.PLAYER_IS_DEAD_MESSAGE, PlayerIsDead.class);
	}

}
