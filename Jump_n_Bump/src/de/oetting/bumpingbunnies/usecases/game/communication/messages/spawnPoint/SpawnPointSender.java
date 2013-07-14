package de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;

public class SpawnPointSender extends MessageSenderTemplate<SpawnPointMessage> {

	public SpawnPointSender(RemoteSender networkSender) {
		super(networkSender, new SpawnPointMetadata());
	}

}
