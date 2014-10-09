package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SendClientPlayerIdSender extends MessageSenderTemplate<Integer> {

	public SendClientPlayerIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.CLIENT_PLAYER_ID);
	}

}
