package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameSettingSender extends MessageSenderTemplate<ServerSettings> {

	public GameSettingSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SERVER_SETTINGS_ID);
	}

}
