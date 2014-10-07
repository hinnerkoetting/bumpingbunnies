package de.oetting.bumpingbunnies.core.networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class UdpSocket implements MySocket {

	private final DatagramSocket socket;
	private final InetAddress destinationAddress;
	private final int destinationPort;
	private final Opponent owner;
	private final DatagramPacket receivingPacket;

	public UdpSocket(DatagramSocket socket, InetAddress address, int port, Opponent owner) {
		super();
		this.socket = socket;
		this.destinationAddress = address;
		this.destinationPort = port;
		this.owner = owner;
		this.receivingPacket = new DatagramPacket(new byte[1024], 1024);
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
	public void connect() {
		// connect is not possible for udp
	}

	@Override
	public void sendMessage(String message) {
		try {
			DatagramPacket packet = new DatagramPacket(message.getBytes(Charset.forName("UTF-8")), message.length(), this.destinationAddress,
					this.destinationPort);
			this.socket.send(packet);
		} catch (IOException e) {
			throw new UdpException(e);
		}
	}

	@Override
	public String blockingReceive() {
		try {
			this.socket.receive(this.receivingPacket);
			return new String(this.receivingPacket.getData(), 0, this.receivingPacket.getLength(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new ReceiveFailure(e);
		}
	}

	@Override
	public Opponent getOwner() {
		return this.owner;
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
	}

	@Override
	public String toString() {
		return "UdpSocket [socket=" + socket + ", address=" + destinationAddress + ", port=" + destinationPort + ", owner=" + owner + ", receivingPacket="
				+ receivingPacket + "]";
	}

	public static class ReceiveFailure extends RuntimeException {
		public ReceiveFailure(Exception e) {
			super(e);
		}
	}

}
