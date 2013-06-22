package de.jumpnbump.usecases.start.communication.wlan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import de.jumpnbump.usecases.start.communication.MySocket;

public class WlanSocket implements MySocket {

	private Socket socket;
	private SocketAddress address;

	public WlanSocket(Socket socket2) {
		this.socket = socket2;
	}

	public WlanSocket(Socket socket2, SocketAddress address) {
		this.socket = socket2;
		this.address = address;
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

	@Override
	public void connect() throws IOException {
		if (this.address != null) {
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
	public OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

}
