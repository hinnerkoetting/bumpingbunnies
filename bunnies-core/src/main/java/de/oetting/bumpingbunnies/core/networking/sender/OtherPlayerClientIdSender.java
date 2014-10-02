package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public class OtherPlayerClientIdSender extends MessageSenderTemplate<PlayerProperties> {

	public OtherPlayerClientIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_OTHER_PLAYER_ID);
	}

}
