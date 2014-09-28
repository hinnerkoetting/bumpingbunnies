package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NoopNetworkReceiveFactory implements NetworkReceiverFactory {

	public static final Logger LOGGER = LoggerFactory.getLogger(NoopNetworkReceiveFactory.class);

	@Override
	public List<NetworkReceiver> create(Player player) {
		LOGGER.info("Noop networkreceive creation");
		return new ArrayList<NetworkReceiver>();
	}

}
