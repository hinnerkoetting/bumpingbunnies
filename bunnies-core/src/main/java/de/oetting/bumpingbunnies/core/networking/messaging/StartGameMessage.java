package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public class StartGameMessage extends MessageMetadata<String> {

	public StartGameMessage() {
		super(MessageId.START_GAME_ID, String.class);
	}

}
