package de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;

public class SendClientPlayerIdSender extends MessageSenderTemplate<Integer> {

	public SendClientPlayerIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageIds.SEND_CLIENT_PLAYER_ID);
	}

}
