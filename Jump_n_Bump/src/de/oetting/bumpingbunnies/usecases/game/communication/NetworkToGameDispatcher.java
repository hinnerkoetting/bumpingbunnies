package de.oetting.bumpingbunnies.usecases.game.communication;

import android.util.SparseArray;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Dispatches incoming traffic to registered listeners;
 * 
 */
public class NetworkToGameDispatcher implements IncomingNetworkDispatcher {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkToGameDispatcher.class);
	private final SparseArray<NetworkListener> listeners;

	public NetworkToGameDispatcher() {
		super();
		this.listeners = new SparseArray<NetworkListener>();
	}

	@Override
	public void dispatchPlayerState(PlayerState playerState) {
		NetworkListener networkListener = this.listeners.get(playerState
				.getId());
		if (networkListener == null) {
			throw new IllegalStateException(
					"No Listener registered for player with id "
							+ playerState.getId());
		}
		networkListener.newMessage(playerState);
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
