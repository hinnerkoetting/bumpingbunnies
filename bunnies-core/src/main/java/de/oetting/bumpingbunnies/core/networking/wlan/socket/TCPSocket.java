package de.oetting.bumpingbunnies.core.networking.wlan.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.exceptions.IORuntimeException;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;

public class TCPSocket extends AbstractSocket implements MySocket {

	private static final Logger LOGGER = LoggerFactory.getLogger(TCPSocket.class);
	private TcpSocketSettings socketSettings;
	private Socket socket;
	private SocketAddress address;

	public TCPSocket(Socket socket, ConnectionIdentifier owner, TcpSocketSettings socketSettings) {
		super(owner);
		this.socket = socket;
		this.socketSettings = socketSettings;
		LOGGER.info("Created Tcp Socket");
	}

	public TCPSocket(Socket socket, SocketAddress address, ConnectionIdentifier owner, TcpSocketSettings socketSettings) {
		super(owner);
		this.socket = socket;
		this.address = address;
		this.socketSettings = socketSettings;
		LOGGER.info("Created Tcp Socket");
	}

	@Override
	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void connect() {
		if (this.address == null) {
			throw new IllegalStateException("Need to set address in constructor");
		}
		try {
			this.socket.connect(this.address);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

	public static class SocketCreationException extends RuntimeException {
		public SocketCreationException(Exception e) {
			super(e);
		}
	}

	@Override
	public boolean isFastSocketPossible() {
		return true;
	}

	public InetAddress getInetAddress() {
		return this.socket.getInetAddress();
	}

	public TcpSocketSettings getSocketSettings() {
		return socketSettings;
	}

	@Override
	public String toString() {
		return "TCPSocket [socket=" + socket + ", address=" + address + "]";
	}

	@Override
	public String getRemoteDescription() {
		return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
	}

	@Override
	public String getLocalDescription() {
		return socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort();
	}

}
