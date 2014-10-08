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
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;

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
		LOGGER.info("Connecting to socket " + NetworkConstants.SERVER_WLAN_PORT);
		SocketAddress socketAddress = new InetSocketAddress(adress, NetworkConstants.SERVER_WLAN_PORT);
		TcpSocketSettings settings = new TcpSocketSettings(socketAddress, NetworkConstants.SERVER_WLAN_PORT, NetworkConstants.SERVER_WLAN_PORT);
		Socket socket = new Socket();
		return new TCPSocket(socket, socketAddress, Opponent.createOpponent("wlan" + adress, OpponentType.WLAN), settings);
	}
}
