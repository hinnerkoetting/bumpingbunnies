package de.oetting.bumpingbunnies.core.networking.messaging.pause;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GamePausedSender extends MessageSenderTemplate<Boolean>{

	public GamePausedSender(NetworkSender networkSender) {
		super(networkSender, MessageId.PAUSE);
	}

}
