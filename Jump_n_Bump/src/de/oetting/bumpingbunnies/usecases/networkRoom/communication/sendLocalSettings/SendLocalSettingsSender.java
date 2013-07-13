package de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendLocalSettings;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;

public class SendLocalSettingsSender extends MessageSenderTemplate<LocalPlayersettings> {

	public SendLocalSettingsSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CLIENT_LOCAL_PLAYER_SETTINGS);
	}

}
