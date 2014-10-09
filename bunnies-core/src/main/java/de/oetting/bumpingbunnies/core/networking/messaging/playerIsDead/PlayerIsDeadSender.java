package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class PlayerIsDeadSender extends MessageSenderTemplate<PlayerIsDeadMessage> {

	public PlayerIsDeadSender(NetworkSender networkSender) {
		super(networkSender, MessageId.PLAYER_IS_DEAD_MESSAGE);
	}
}
