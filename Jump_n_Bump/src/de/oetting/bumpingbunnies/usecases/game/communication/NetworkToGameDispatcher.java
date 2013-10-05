package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.Map;
import java.util.TreeMap;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

/**
 * Dispatches incoming traffic to registered listeners. If no listener is registered for an ID, an exception is thrown.
 * 
 */
public class NetworkToGameDispatcher implements IncomingNetworkDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkToGameDispatcher.class);
	private final Map<Integer, NetworkListener> listeners;

	public NetworkToGameDispatcher() {
		super();
		this.listeners = new TreeMap<Integer, NetworkListener>();
	}

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		NetworkListener networkListener = this.listeners.get(wrapper.getId().ordinal());
		if (networkListener == null) {
			throw new NoListenerFound(wrapper.getId());
		}
		LOGGER.debug("Received message %s", wrapper.getMessage());
		networkListener.newMessage(wrapper);
	}

	public void addObserver(MessageId id, NetworkListener listener) {
		LOGGER.debug("Registering listener with id %d", id.ordinal());
		this.listeners.put(id.ordinal(), listener);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this;
	}

	public static class NoListenerFound extends RuntimeException {
		public NoListenerFound(MessageId messageId) {
			super(String.format("No listener found for id %s", messageId.toString()));
		}
	}
}
