package de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public class StartGameSender extends MessageSenderTemplate<String> {

	public StartGameSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.START_GAME_ID);
	}

}
