package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
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
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	public void close() {
		this.socket.close();
	}

	public void receive(DatagramPacket packet) {
		try {
			this.socket.receive(packet);
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	public static class UdpException extends RuntimeException {

		public UdpException(Throwable throwable) {
			super(throwable);
		}
	}
}
