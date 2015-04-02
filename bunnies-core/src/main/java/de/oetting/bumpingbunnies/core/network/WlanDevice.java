package de.oetting.bumpingbunnies.core.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.networking.FreePortFinder;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;

public class WlanDevice implements ServerDevice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WlanSocketFactory.class);
	private final String address;
	private final FreePortFinder freePortFinder;
	private final String name;

	public WlanDevice(String address, String name) {
		this.address = address;
		this.name = name;
		freePortFinder = new FreePortFinder();
	}

	public WlanDevice(InetAddress address, String name) {
		this.name = name;
		this.address = address.getHostAddress();
		freePortFinder = new FreePortFinder();
	}

	@Override
	public MySocket createClientSocket() {
		String address = WlanDevice.this.address;
		int remotePort = NetworkConstants.SERVER_NETWORK_PORT;
		int localPort = freePortFinder.findFreePort();
		LOGGER.info("Connecting to socket on local port %s", localPort);
		SocketAddress socketAddress = new InetSocketAddress(address, remotePort);
		TcpSocketSettings settings = new TcpSocketSettings(socketAddress, localPort, NetworkConstants.SERVER_NETWORK_PORT);
		Socket socket = new Socket();
		bindToLocalPort(localPort, socket);
		return new TCPSocket(socket, socketAddress, ConnectionIdentifierFactory.createWlanPlayer(address, localPort), settings);
	}

	private void bindToLocalPort(int localPort, Socket socket) {
		try {
			socket.bind(new InetSocketAddress(localPort));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WlanDevice other = (WlanDevice) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return name;
	}
}
