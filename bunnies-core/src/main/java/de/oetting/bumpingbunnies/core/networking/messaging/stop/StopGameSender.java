package de.oetting.bumpingbunnies.core.networking.messaging.stop;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.networking.MessageId;

public class StopGameSender extends MessageSenderTemplate<String> {

	public StopGameSender(NetworkSender networkSender) {
		super(networkSender, MessageId.STOP_GAME);
	}

}
