package de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame;

import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public class StartGameMessage extends MessageMetadata<String> {

	public StartGameMessage() {
		super(MessageId.START_GAME_ID, String.class);
	}

}
