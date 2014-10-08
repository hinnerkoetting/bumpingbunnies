package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.MessageSenderTemplate;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SendRemoteSettingsSender extends MessageSenderTemplate<RemoteSettings> {

	public SendRemoteSettingsSender(SimpleNetworkSender networkSender) {
		super(networkSender, MessageId.SEND_CLIENT_REMOTE_SETTINGS);
	}

}
