package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageMetadata;

public class SpawnPointMetadata extends MessageMetadata<SpawnPointMessage> {

	public SpawnPointMetadata() {
		super(MessageId.SPAWN_POINT, SpawnPointMessage.class);
	}

}
