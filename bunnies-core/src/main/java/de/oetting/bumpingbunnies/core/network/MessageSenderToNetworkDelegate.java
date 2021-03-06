package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.network.MessageId;

public class MessageSenderToNetworkDelegate implements MessageSender {

	private final NetworkMessageDistributor sendControl;

	public MessageSenderToNetworkDelegate(NetworkMessageDistributor sendControl) {
		super();
		this.sendControl = sendControl;
	}

	@Override
	public void sendMessage(MessageId messageId, Object message) {
		sendControl.sendMessage(messageId, message);
	}
}
