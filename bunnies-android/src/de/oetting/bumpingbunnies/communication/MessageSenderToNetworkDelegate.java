package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.MessageSender;
import de.oetting.bumpingbunnies.model.networking.MessageId;

public class MessageSenderToNetworkDelegate implements MessageSender {

	private final NetworkSendControl sendControl;

	public MessageSenderToNetworkDelegate(NetworkSendControl sendControl) {
		super();
		this.sendControl = sendControl;
	}

	@Override
	public void sendMessage(MessageId messageId, Object message) {
		sendControl.sendMessage(messageId, message);
	}
}
