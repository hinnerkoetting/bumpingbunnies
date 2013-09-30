package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSocket implements MySocket {

	private final DatagramSocket socket;
	private final InetAddress address;
	private final int port;
	private DatagramPacket receivingPacket;

	public UdpSocket(DatagramSocket socket, InetAddress address, int port) {
		super();
		this.socket = socket;
		this.address = address;
		this.port = port;
		this.receivingPacket = new DatagramPacket(new byte[1024], 1024);
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	@Override
	public void close() {
		this.socket.close();
	}

	@Deprecated
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
			DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), this.address, this.port);
			this.socket.send(packet);
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	@Override
	public String blockingReceive() {
		try {
			this.socket.receive(this.receivingPacket);
			return new String(this.receivingPacket.getData(), 0, this.receivingPacket.getLength());
		} catch (IOException e) {
			throw new ReceiveFailure(e);
		}
	}

	@Override
	public MySocket createFastConnection() {
		return this;
	}

	public static class ReceiveFailure extends RuntimeException {
		public ReceiveFailure(Exception e) {
			super(e);
		}
	}
}
