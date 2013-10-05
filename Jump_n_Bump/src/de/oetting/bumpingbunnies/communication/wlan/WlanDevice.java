package de.oetting.bumpingbunnies.communication.wlan;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class WlanDevice implements ServerDevice {

	private final String address;

	public WlanDevice(String address) {
		this.address = address;
	}

	@Override
	public MySocket createClientSocket() {
		String adress = WlanDevice.this.address;
		try {
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(adress,
					NetworkConstants.WLAN_PORT);
			return new WlanSocket(socket, address, Opponent.createOpponent("wlan" + adress));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
