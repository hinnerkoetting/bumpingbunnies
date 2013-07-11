package de.oetting.bumpingbunnies.communication.messageInterface;

import de.oetting.bumpingbunnies.usecases.game.communication.DefaultNetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;

public abstract class MessageReceiverTemplate<T> implements MessageInterface<T> {

	private DefaultNetworkListener<T> listener;

	public MessageReceiverTemplate(NetworkToGameDispatcher dispatcher, int messageId, Class<T> clazz) {
		this.listener = new DefaultNetworkListener<T>(clazz) {

			@Override
			public void receiveMessage(T object) {
				message(object);
			}
		};
		dispatcher.addObserver(messageId, this.listener);
	}

}
