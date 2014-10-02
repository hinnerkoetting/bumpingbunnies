package de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class SpawnPointMetadata extends MessageMetadata<SpawnPointMessage> {

	public SpawnPointMetadata() {
		super(MessageId.SPAWN_POINT, SpawnPointMessage.class);
	}

}
