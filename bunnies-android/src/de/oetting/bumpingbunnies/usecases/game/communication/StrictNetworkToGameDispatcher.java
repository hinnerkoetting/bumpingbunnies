package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.NetworkListener;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;

/**
 * Throws exceptions if no listener is registered for a message.
 * 
 */
public class StrictNetworkToGameDispatcher extends NetworkToGameDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasyNetworkToGameDispatcher.class);

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		NetworkListener networkListener = this.getListeners().get(wrapper.getId().ordinal());
		if (networkListener == null) {
			throw new NoListenerFound(wrapper.getId());
		}
		LOGGER.debug("Received message %s", wrapper.getMessage());
		networkListener.newMessage(wrapper);
	}
}
