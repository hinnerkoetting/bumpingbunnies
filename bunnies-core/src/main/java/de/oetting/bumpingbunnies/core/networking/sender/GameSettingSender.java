package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;

public class GameSettingSender extends MessageSenderTemplate<GeneralSettings> {

	public GameSettingSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CONFIGURATION_ID);
	}

}
