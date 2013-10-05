package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;

public class SpawnPointSender extends MessageSenderTemplate<SpawnPointMessage> {

	public SpawnPointSender(ThreadedNetworkSender networkSender) {
		super(networkSender, new SpawnPointMetadata());
	}

}
