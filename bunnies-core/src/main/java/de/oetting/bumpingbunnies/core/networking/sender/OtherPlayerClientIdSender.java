package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class OtherPlayerClientIdSender extends MessageSenderTemplate<PlayerProperties> {

	public OtherPlayerClientIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_OTHER_PLAYER_ID);
	}

}
