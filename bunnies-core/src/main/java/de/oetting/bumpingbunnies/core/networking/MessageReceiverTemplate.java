package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageInterface;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.model.networking.MessageMetadata;

public abstract class MessageReceiverTemplate<T> implements MessageInterface<T> {

	private NetworkListener listener;

	public MessageReceiverTemplate(NetworkToGameDispatcher dispatcher, MessageMetadata<T> msg) {
		this(dispatcher, msg.getId(), msg.getClazz());
	}

	public MessageReceiverTemplate(NetworkToGameDispatcher dispatcher, MessageId messageId, Class<T> clazz) {
		this.listener = new DefaultNetworkListener<T>(clazz) {

			@Override
			public void receiveMessage(T object) {
				onReceiveMessage(object);
			}
		};
		dispatcher.addObserver(messageId, this.listener);
	}

	public MessageReceiverTemplate(NetworkToGameDispatcher dispatcher, NetworkListener listener, MessageId messageId) {
		this.listener = listener;
		dispatcher.addObserver(messageId, this.listener);
	}

	public abstract void onReceiveMessage(T object);
}