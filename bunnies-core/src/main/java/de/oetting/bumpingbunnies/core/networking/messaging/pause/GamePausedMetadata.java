package de.oetting.bumpingbunnies.core.networking.messaging.pause;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class GamePausedMetadata extends MessageMetadata<Boolean >{

	public GamePausedMetadata() {
		super(MessageId.PAUSE, Boolean.class);
	}

}
