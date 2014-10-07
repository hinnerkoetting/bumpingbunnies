package de.oetting.bumpingbunnies.core.network;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class WlanDevice implements ServerDevice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WlanSocketFactory.class);
	private final String address;

	public WlanDevice(String address) {
		this.address = address;
	}

	public WlanDevice(InetAddress address) {
		this.address = address.getHostAddress();
	}

	@Override
	public MySocket createClientSocket() {
		String adress = WlanDevice.this.address;
		try {
			LOGGER.info("Connecting to socket " + NetworkConstants.SERVER_WLAN_PORT);
			Socket socket = new Socket();
			SocketAddress address = new InetSocketAddress(adress, NetworkConstants.SERVER_WLAN_PORT);
			return new TCPSocket(socket, address, Opponent.createOpponent("wlan" + adress, OpponentType.WLAN));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
