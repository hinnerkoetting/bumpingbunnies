package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;

public class PlayerIsDeadSender extends MessageSenderTemplate<PlayerIsDead> {

	public PlayerIsDeadSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageIds.PLAYER_IS_DEAD_MESSAGE);
	}

}
