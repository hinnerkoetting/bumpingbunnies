package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public class PlayerIsDeadSender extends MessageSenderTemplate<PlayerIsDead> {

	public PlayerIsDeadSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.PLAYER_IS_DEAD_MESSAGE);
	}

}
