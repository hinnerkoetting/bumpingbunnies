package de.oetting.bumpingbunnies.core.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.core.networking.FreePortFinder;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;

public class WlanDevice implements ServerDevice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WlanSocketFactory.class);
	private final String address;
	private final FreePortFinder freePortFinder;

	public WlanDevice(String address) {
		this.address = address;
		freePortFinder = new FreePortFinder();
	}

	public WlanDevice(InetAddress address) {
		this.address = address.getHostAddress();
		freePortFinder = new FreePortFinder();
	}

	@Override
	public MySocket createClientSocket() {
		String adress = WlanDevice.this.address;
		int remotePort = NetworkConstants.SERVER_WLAN_PORT;
		int localPort = freePortFinder.findFreePort();
		LOGGER.info("Connecting to socket on local port %s", localPort);
		SocketAddress socketAddress = new InetSocketAddress(adress, remotePort);
		TcpSocketSettings settings = new TcpSocketSettings(socketAddress, localPort, NetworkConstants.SERVER_WLAN_PORT);
		Socket socket = new Socket();
		bindToLocalPort(localPort, socket);
		return new TCPSocket(socket, socketAddress, Opponent.createOpponent("wlan" + adress, OpponentType.WLAN), settings);
	}

	private void bindToLocalPort(int localPort, Socket socket) {
		try {
			socket.bind(new InetSocketAddress(localPort));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
