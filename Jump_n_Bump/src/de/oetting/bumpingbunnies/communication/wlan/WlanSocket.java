package de.oetting.bumpingbunnies.communication.wlan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.UdpSocket;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class WlanSocket extends AbstractSocket implements MySocket {

	private Socket socket;
	private SocketAddress address;
	private UdpSocket udpSocket;

	public WlanSocket(Socket socket, Opponent owner) throws IOException {
		super(owner);
		this.socket = socket;
	}

	public WlanSocket(Socket socket, SocketAddress address, Opponent owner) throws IOException {
		super(owner);
		this.socket = socket;
		this.address = address;
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

	@Override
	public void connect() throws IOException {
		if (this.address == null) {
			throw new IllegalStateException(
					"Need to set address in constructor");
		}
		this.socket.connect(this.address);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

	@Override
	public MySocket createFastConnection() {
		if (this.udpSocket == null) {
			this.udpSocket = createUdpSocket();
		}
		return this.udpSocket;
	}

	private UdpSocket createUdpSocket() {
		try {
			DatagramSocket dataSocket = new DatagramSocket(NetworkConstants.UDP_PORT);
			dataSocket.setBroadcast(false);
			return new UdpSocket(dataSocket, this.socket.getInetAddress(), NetworkConstants.UDP_PORT, getOwner());
		} catch (IOException e) {
			throw new SocketCreationException(e);
		}
	}

	public static class SocketCreationException extends RuntimeException {
		public SocketCreationException(Exception e) {
			super(e);
		}
	}
}
