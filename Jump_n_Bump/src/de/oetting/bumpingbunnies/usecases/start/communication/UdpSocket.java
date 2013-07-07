package de.oetting.bumpingbunnies.usecases.start.communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSocket {

	private final DatagramSocket socket;
	private final InetAddress address;

	public UdpSocket(DatagramSocket socket, InetAddress address) {
		super();
		this.socket = socket;
		this.address = address;
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	public void send(DatagramPacket packet) {
		try {
			this.socket.send(packet);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		this.socket.close();
	}
}
