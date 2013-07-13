package de.oetting.bumpingbunnies.usecases.game.communication.messages.stop;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;

public class StopGameSender extends MessageSenderTemplate<String> {

	public StopGameSender(NetworkSender networkSender) {
		super(networkSender, MessageIds.STOP_GAME);
	}

}
