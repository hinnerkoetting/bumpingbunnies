package de.oetting.bumpingbunnies.usecases.game.communication;

import android.util.SparseArray;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

/**
 * Dispatches incoming traffic to registered listeners;
 * 
 */
public class NetworkToGameDispatcher implements IncomingNetworkDispatcher {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkToGameDispatcher.class);
	private final SparseArray<NetworkListener> listeners;
	private final MessageParser parser;

	public NetworkToGameDispatcher(MessageParser parser) {
		super();
		this.parser = parser;
		this.listeners = new SparseArray<NetworkListener>();
	}

	@Override
	public void dispatchPlayerState(JsonWrapper wrapper) {
		NetworkListener networkListener = this.listeners.get(wrapper.getId());
		if (networkListener == null) {
			throw new IllegalStateException("No Listener registered for id "
					+ wrapper.getId());
		}
		LOGGER.debug("Received message %s", wrapper.getMessage());
		networkListener.newMessage(wrapper.getMessage());
	}

	public void addObserver(int id, NetworkListener listener) {
		LOGGER.debug("Registering listener with id %d", id);
		this.listeners.put(id, listener);
	}

	@Override
	public NetworkToGameDispatcher getNetworkToGameDispatcher() {
		return this;
	}
}
