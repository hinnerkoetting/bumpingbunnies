package de.jumpnbump.usecases.start.communication.wlan;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.jumpnbump.usecases.game.communication.NetworkConstants;
import de.jumpnbump.usecases.start.communication.MySocket;
import de.jumpnbump.usecases.start.communication.ServerDevice;

public class WlanDevice implements ServerDevice {

	@Override
	public MySocket createClientSocket() {
		String adress = "192.168.0.110";
		try {
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(adress,
					NetworkConstants.WLAN_PORT);
			return new WlanSocket(socket, address);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
