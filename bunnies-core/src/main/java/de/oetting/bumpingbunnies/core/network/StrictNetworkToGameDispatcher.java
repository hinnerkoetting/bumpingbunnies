package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

/**
 * Throws exceptions if no listener is registered for a message.
 * 
 */
public class StrictNetworkToGameDispatcher extends NetworkToGameDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(StrictNetworkToGameDispatcher.class);

	private final PlayerDisconnectedCallback callback;

	public StrictNetworkToGameDispatcher(PlayerDisconnectedCallback callback) {
		this.callback = callback;
	}

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		NetworkListener networkListener = this.getListeners().get(wrapper.getId().ordinal());
		if (networkListener == null) {
			throw new NoListenerFound(wrapper.getId());
		}
		LOGGER.debug("Received message %s", wrapper.getMessage());
		networkListener.newMessage(wrapper);
	}

	@Override
	public void playerWasDisconnected(ConnectionIdentifier owner) {
		callback.playerDisconnected(owner);
	}
}
