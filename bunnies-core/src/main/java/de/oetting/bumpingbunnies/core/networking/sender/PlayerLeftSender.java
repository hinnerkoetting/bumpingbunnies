package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class PlayerLeftSender extends MessageSenderTemplate<PlayerProperties> {

	public PlayerLeftSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.PLAYER_DISCONNECTED);
	}

}
