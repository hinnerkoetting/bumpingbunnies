package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.networking.sockets.wlan.WlanServerSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class WlanSocketFactory implements SocketFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(WlanSocketFactory.class);

	@Override
	public ServerSocket create() {
		try {
			LOGGER.info("Listening on socket " + NetworkConstants.WLAN_PORT);
			java.net.ServerSocket serverSocket = new java.net.ServerSocket(
					NetworkConstants.WLAN_PORT);
			return new WlanServerSocket(serverSocket);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public MySocket createClientSocket(ServerDevice serverDevice) {
		return serverDevice.createClientSocket();
	}

}
