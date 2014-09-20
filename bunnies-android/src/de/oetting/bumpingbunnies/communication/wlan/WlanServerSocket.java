package de.oetting.bumpingbunnies.communication.wlan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

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
		return new TCPSocket(socket, Opponent.createOpponent("server" + this.serverSocket.getInetAddress().getHostAddress(),
				OpponentType.WLAN));
	}

	@Override
	public void close() throws IOException {
		this.serverSocket.close();
	}

}
