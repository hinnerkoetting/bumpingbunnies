package de.oetting.bumpingbunnies.usecases.networkRoom.communication.otherPlayerId;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public class OtherPlayerClientIdSender extends MessageSenderTemplate<PlayerProperties> {

	public OtherPlayerClientIdSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageIds.SEND_OTHER_PLAYER_ID);
	}

}
