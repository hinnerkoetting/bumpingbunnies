package de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class BunnyIsDeadMessageReceivedSender extends MessageSenderTemplate<Integer> {

	public BunnyIsDeadMessageReceivedSender(NetworkSender networkSender) {
		super(networkSender, MessageId.BUNNY_DEAD_RECEIVED);
	}
}
