package de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;

public class SpawnPointSender extends MessageSenderTemplate<SpawnPointMessage> {

	public SpawnPointSender(NetworkSender networkSender) {
		super(networkSender, new SpawnPointMetadata());
	}

}
