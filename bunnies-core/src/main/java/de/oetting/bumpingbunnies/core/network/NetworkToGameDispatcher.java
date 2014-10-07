package de.oetting.bumpingbunnies.core.network;

import java.util.Map;
import java.util.TreeMap;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Dispatches incoming traffic to registered listeners. If no listener is
 * registered for an ID, an exception is thrown.
 * 
 */
public abstract class NetworkToGameDispatcher implements IncomingNetworkDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkToGameDispatcher.class);
	private final Map<Integer, NetworkListener> listeners;

	public NetworkToGameDispatcher() {
		this.listeners = new TreeMap<Integer, NetworkListener>();
	}

	public void addObserver(MessageId id, NetworkListener listener) {
		LOGGER.debug("Registering listener with id %d", id.ordinal());
		this.getListeners().put(id.ordinal(), listener);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this;
	}

	public Map<Integer, NetworkListener> getListeners() {
		return listeners;
	}

	public static class NoListenerFound extends RuntimeException {
		public NoListenerFound(MessageId messageId) {
			super(String.format("No listener found for id %s", messageId.toString()));
		}
	}
}
