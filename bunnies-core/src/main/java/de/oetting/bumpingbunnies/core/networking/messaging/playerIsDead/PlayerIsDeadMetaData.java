package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class PlayerIsDeadMetaData extends MessageMetadata<PlayerIsDeadMessage> {

	public PlayerIsDeadMetaData() {
		super(MessageId.PLAYER_IS_DEAD_MESSAGE, PlayerIsDeadMessage.class);
	}

}
