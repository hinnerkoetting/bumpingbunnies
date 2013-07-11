package de.oetting.bumpingbunnies.usecases.game.communication.messages;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.model.messages.PlayerIsDead;

public class PlayerIsDeadSender extends MessageSenderTemplate<PlayerIsDead> {

	public PlayerIsDeadSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageIds.PLAYER_IS_DEAD_MESSAGE);
	}

}
