package de.oetting.bumpingbunnies.core.network;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.networking.sockets.wlan.WlanServerSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class WlanSocketFactory implements SocketFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(WlanSocketFactory.class);

	@Override
	public ServerSocket create() {
		try {
			LOGGER.info("Listening on TCP socket " + NetworkConstants.SERVER_NETWORK_PORT);
			java.net.ServerSocket serverSocket = new java.net.ServerSocket(NetworkConstants.SERVER_NETWORK_PORT);
			return new WlanServerSocket(serverSocket);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public NetworkType forNetworkType() {
		return NetworkType.WLAN;
	}

}
