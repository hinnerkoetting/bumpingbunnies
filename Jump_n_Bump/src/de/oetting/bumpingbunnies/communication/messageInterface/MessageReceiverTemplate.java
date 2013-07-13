package de.oetting.bumpingbunnies.communication.messageInterface;

import de.oetting.bumpingbunnies.usecases.game.communication.DefaultNetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public abstract class MessageReceiverTemplate<T> implements MessageInterface<T> {

	private DefaultNetworkListener<T> listener;

	public MessageReceiverTemplate(NetworkToGameDispatcher dispatcher, MessageId messageId, Class<T> clazz) {
		this.listener = new DefaultNetworkListener<T>(clazz) {

			@Override
			public void receiveMessage(T object) {
				onReceiveMessage(object);
			}
		};
		dispatcher.addObserver(messageId, this.listener);
	}

	public abstract void onReceiveMessage(T object);
}
