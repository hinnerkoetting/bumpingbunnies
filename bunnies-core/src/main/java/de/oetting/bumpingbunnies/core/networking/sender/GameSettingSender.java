package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameSettingSender extends MessageSenderTemplate<GeneralSettings> {

	public GameSettingSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CONFIGURATION_ID);
	}

}
