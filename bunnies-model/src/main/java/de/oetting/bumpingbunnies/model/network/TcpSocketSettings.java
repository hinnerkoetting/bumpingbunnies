package de.oetting.bumpingbunnies.model.network;

import java.net.SocketAddress;

public class TcpSocketSettings {

	private final SocketAddress destinationAddress;
	private final int localPort;
	private final int destinationPort;

	public TcpSocketSettings(SocketAddress destinationAddress, int clientPort, int destinationPort) {
		this.destinationAddress = destinationAddress;
		this.localPort = clientPort;
		this.destinationPort = destinationPort;
	}

	public SocketAddress getDestinationAddress() {
		return destinationAddress;
	}

	public int getLocalPort() {
		return localPort;
	}

	public int getDestinationPort() {
		return destinationPort;
	}

}
