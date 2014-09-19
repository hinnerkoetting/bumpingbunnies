package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;

public class SpawnPointSender extends MessageSenderTemplate<SpawnPointMessage> {

	public SpawnPointSender(NetworkSender networkSender) {
		super(networkSender, new SpawnPointMetadata());
	}

}
