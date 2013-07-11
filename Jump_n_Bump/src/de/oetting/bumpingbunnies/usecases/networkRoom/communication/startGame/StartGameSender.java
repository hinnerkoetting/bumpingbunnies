package de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;

public class StartGameSender extends MessageSenderTemplate<String> {

	public StartGameSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageIds.START_GAME_ID);
	}

}
