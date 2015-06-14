package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Throws exceptions if no listener is registered for a message.
 * 
 */
public class StrictNetworkToGameDispatcher extends NetworkToGameDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(StrictNetworkToGameDispatcher.class);

	private final PlayerDisconnectedCallback callback;
	private final List<JsonWrapper> storedMessages = new CopyOnWriteArrayList<JsonWrapper>();

	public StrictNetworkToGameDispatcher(PlayerDisconnectedCallback callback) {
		this.callback = callback;
	}

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		NetworkListener networkListener = this.getListeners().get(wrapper.getId().ordinal());
		if (networkListener == null) {
			storedMessages.add(wrapper);
			LOGGER.warn("Received message but no listener exists until now. Storing the message until later");
			return;
		}
		LOGGER.debug("Received message %s", wrapper.getMessage());
		networkListener.newMessage(wrapper);
	}

	@Override
	public void addObserver(MessageId id, NetworkListener listener) {
		super.addObserver(id, listener);
		for (JsonWrapper existingMessage: storedMessages) {
			if (existingMessage.getId().equals(id)) {
				listener.newMessage(existingMessage);
			}
			storedMessages.remove(existingMessage);
		}
	}
	@Override
	public void playerWasDisconnected(ConnectionIdentifier owner) {
		callback.playerDisconnected(owner);
	}
}
