package de.oetting.bumpingbunnies.usecases.networkRoom.communication.generalSettings;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;

public class GameSettingSender extends MessageSenderTemplate<GeneralSettings> {

	public GameSettingSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CONFIGURATION_ID);
	}

}
