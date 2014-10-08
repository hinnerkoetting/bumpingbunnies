package de.oetting.bumpingbunnies.core.networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class UdpSocket implements MySocket {

	private final DatagramSocket socket;
	private final InetAddress destinationAddress;
	private final Opponent owner;
	/**
	 * cache for incoming messages
	 */
	private final DatagramPacket receivingPacket;
	private final int destinationPort;

	UdpSocket(DatagramSocket socket, Opponent owner, UdpSocketSettings settings) {
		this.socket = socket;
		this.destinationAddress = settings.getDestinationAddress();
		this.destinationPort = settings.getDestinationPort();
		this.owner = owner;
		this.receivingPacket = new DatagramPacket(new byte[1024], 1024);
	}

	@Override
	public void close() {
		this.socket.close();
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

	public static class UdpException extends RuntimeException {

		public UdpException(Throwable throwable) {
			super(throwable);
		}
	}
}
