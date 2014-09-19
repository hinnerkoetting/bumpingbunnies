package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.communication.ServerSocket;
import de.oetting.bumpingbunnies.communication.SocketFactory;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;

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
