package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class NoopNetworkReceiveFactory implements NetworkReceiverFactory {

	public static final Logger LOGGER = LoggerFactory.getLogger(NoopNetworkReceiveFactory.class);

	@Override
	public List<NetworkReceiver> create(MySocket socket) {
		LOGGER.info("Noop networkreceive creation");
		return new ArrayList<NetworkReceiver>();
	}

}
