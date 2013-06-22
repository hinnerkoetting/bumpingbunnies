package de.jumpnbump.usecases.start.communication.wlan;

import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.start.communication.MySocket;
import de.jumpnbump.usecases.start.communication.ServerDevice;
import de.jumpnbump.usecases.start.communication.ServerSocket;
import de.jumpnbump.usecases.start.communication.SocketFactory;

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
