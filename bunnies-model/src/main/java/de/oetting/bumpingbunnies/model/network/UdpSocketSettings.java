package de.oetting.bumpingbunnies.model.network;

import java.net.InetAddress;

public class UdpSocketSettings {

	private final InetAddress destinationAddress;
	private final int localPort;
	private final int destinationPort;

	public UdpSocketSettings(InetAddress destinationAddress, int clientPort, int destinationPort) {
		this.destinationAddress = destinationAddress;
		this.localPort = clientPort;
		this.destinationPort = destinationPort;
	}

	public InetAddress getDestinationAddress() {
		return destinationAddress;
	}

	public int getLocalPort() {
		return localPort;
	}

	public int getDestinationPort() {
		return destinationPort;
	}

}
