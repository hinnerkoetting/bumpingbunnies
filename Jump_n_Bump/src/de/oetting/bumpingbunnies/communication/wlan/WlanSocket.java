package de.oetting.bumpingbunnies.communication.wlan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.communication.MySocket;

public class WlanSocket extends AbstractSocket implements MySocket {

	private Socket socket;
	private SocketAddress address;

	public WlanSocket(Socket socket) throws IOException {
		this.socket = socket;
	}

	public WlanSocket(Socket socket, SocketAddress address) throws IOException {
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

}
