package de.oetting.bumpingbunnies.usecases.start.communication.wlan;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.start.communication.ServerSocket;
import de.oetting.bumpingbunnies.usecases.start.communication.SocketFactory;

public class WlanServerSocketFactory implements SocketFactory {

	@Override
	public ServerSocket create() {
		try {
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
