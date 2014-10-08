package de.oetting.bumpingbunnies.core.networking.sockets.wlan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;

public class WlanServerSocket implements de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket {

	private ServerSocket serverSocket;

	public WlanServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public MySocket accept() throws IOException {
		Socket socket = this.serverSocket.accept();
		TcpSocketSettings settings = new TcpSocketSettings(null, socket.getLocalPort(), socket.getPort());
		return new TCPSocket(socket, Opponent.createOpponent("server" + this.serverSocket.getInetAddress().getHostAddress(), OpponentType.WLAN), settings);
	}

	@Override
	public void close() throws IOException {
		this.serverSocket.close();
	}

}
