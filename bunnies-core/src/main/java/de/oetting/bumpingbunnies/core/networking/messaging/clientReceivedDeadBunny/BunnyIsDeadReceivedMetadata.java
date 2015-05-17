package de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class BunnyIsDeadReceivedMetadata extends MessageMetadata<Integer> {

	public BunnyIsDeadReceivedMetadata() {
		super(MessageId.BUNNY_DEAD_RECEIVED, Integer.class);
	}

}
