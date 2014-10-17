package de.oetting.bumpingbunnies.core.networking.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class UdpSocket implements MySocket {

	private final DatagramSocket socket;
	private final ConnectionIdentifier owner;
	/**
	 * cache for incoming messages
	 */
	private final DatagramPacket receivingPacket;
	private UdpSocketSettings settings;

	UdpSocket(DatagramSocket socket, ConnectionIdentifier owner, UdpSocketSettings settings) {
		this.socket = socket;
		this.settings = settings;
		this.owner = owner;
		this.receivingPacket = new DatagramPacket(new byte[1024], 1024);
	}

	@Override
	public void close() {
		this.socket.close();
	}

	@Override
	public void connect() {
		try {
			SocketAddress localSocketAddress = new InetSocketAddress(settings.getLocalPort());
			socket.bind(localSocketAddress);
		} catch (SocketException e) {
			throw new UdpException(e);
		}
	}

	@Override
	public void sendMessage(String message) {
		try {
			DatagramPacket packet = new DatagramPacket(message.getBytes(Charset.forName("UTF-8")), message.length(), this.settings.getDestinationAddress(),
					this.settings.getDestinationPort());
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
	public ConnectionIdentifier getOwner() {
		return this.owner;
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
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

	@Override
	public String getRemoteDescription() {
		return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
	}

	@Override
	public String getLocalDescription() {
		return socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort();
	}

	@Override
	public String toString() {
		return "UdpSocket [socket=" + socket + ", owner=" + owner + ", receivingPacket=" + receivingPacket + ", settings=" + settings + "]";
	}

}
