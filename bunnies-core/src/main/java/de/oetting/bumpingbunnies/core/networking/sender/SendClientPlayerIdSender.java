package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.networking.MessageId;

public class SendClientPlayerIdSender extends MessageSenderTemplate<Integer> {

	public SendClientPlayerIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CLIENT_PLAYER_ID);
	}

}
