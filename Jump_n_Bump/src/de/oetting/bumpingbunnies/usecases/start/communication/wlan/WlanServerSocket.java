package de.oetting.bumpingbunnies.usecases.start.communication.wlan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class WlanServerSocket implements
		de.oetting.bumpingbunnies.usecases.start.communication.ServerSocket {

	private ServerSocket serverSocket;

	public WlanServerSocket(ServerSocket serverSocket) {
		super();
		this.serverSocket = serverSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		Socket socket = this.serverSocket.accept();
		return new WlanSocket(socket);
	}

	@Override
	public void close() throws IOException {
		this.serverSocket.close();
	}

}
