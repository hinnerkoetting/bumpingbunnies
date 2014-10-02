package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.networking.MessageId;

public interface MessageSender {

	void sendMessage(MessageId messageId, Object message);
}
