package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.NetworkListener;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;

/**
 * Does allow messages which do not have registered listeners
 * 
 */
public class EasyNetworkToGameDispatcher extends NetworkToGameDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasyNetworkToGameDispatcher.class);

	@Override
	public void dispatchMessage(JsonWrapper wrapper) {
		NetworkListener networkListener = this.getListeners().get(wrapper.getId().ordinal());
		if (networkListener == null) {
			LOGGER.debug("No listener registered. Throwing away message... %s", wrapper.getId());
			return;
		}
		LOGGER.debug("Received message %s", wrapper);
		networkListener.newMessage(wrapper);
	}
}
