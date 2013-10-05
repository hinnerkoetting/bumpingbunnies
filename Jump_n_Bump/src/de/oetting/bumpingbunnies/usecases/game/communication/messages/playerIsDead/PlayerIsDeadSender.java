package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public class PlayerIsDeadSender extends MessageSenderTemplate<PlayerIsDead> {

	public PlayerIsDeadSender(ThreadedNetworkSender networkSender) {
		super(networkSender, MessageId.PLAYER_IS_DEAD_MESSAGE);
	}

}
