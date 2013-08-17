package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSocket implements MySocket {

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

	/**
	 * use #send(String) instead
	 */
	@Deprecated
	public void send(DatagramPacket packet) {
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	@Override
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

	@Override
	public void connect() throws IOException {
	}

	@Override
	public void sendMessage(String message) {
		try {
			DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), this.address, this.socket.getPort());
			this.socket.send(packet);
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	@Override
	public String blockingReceive() {
		// TODO Auto-generated method stub
		return null;
	}
}
