package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageMetadata;

public class PlayerIsDeadMetaData extends MessageMetadata<PlayerIsDead> {

	public PlayerIsDeadMetaData() {
		super(MessageId.PLAYER_IS_DEAD_MESSAGE, PlayerIsDead.class);
	}

}
