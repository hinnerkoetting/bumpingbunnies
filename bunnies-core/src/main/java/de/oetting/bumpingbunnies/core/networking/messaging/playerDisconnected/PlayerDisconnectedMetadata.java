package de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected;

import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.model.network.MessageMetadata;

public class PlayerDisconnectedMetadata extends MessageMetadata<PlayerDisconnectedMessage> {

	public PlayerDisconnectedMetadata() {
		super(MessageId.PLAYER_DISCONNECTED, PlayerDisconnectedMessage.class);
	}

}
