package de.oetting.bumpingbunnies.communication.wlan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class WlanServerSocket implements
		de.oetting.bumpingbunnies.communication.ServerSocket {

	private ServerSocket serverSocket;

	public WlanServerSocket(ServerSocket serverSocket) {
		super();
		this.serverSocket = serverSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		Socket socket = this.serverSocket.accept();
		return new WlanSocket(socket, Opponent.createOpponent("server" + this.serverSocket.getInetAddress().getHostAddress()));
	}

	@Override
	public void close() throws IOException {
		this.serverSocket.close();
	}

}
