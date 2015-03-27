package de.oetting.bumpingbunnies.core.networking.init;

import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerEstablisher;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;

public class DefaultConnectionEstablisher implements ConnectionEstablisher {

	private final SocketFactory serverSocketFactory;
	private final ConnectsToServer connectsToServer;
	private ConnectionToServerEstablisher connectThread;

	public DefaultConnectionEstablisher(ConnectsToServer connectsToServer, SocketFactory serverSocketFactory) {
		this.connectsToServer = connectsToServer;
		this.serverSocketFactory = serverSocketFactory;
		this.connectThread = null;
	}

	@Override
	public void closeConnections() {
	}

	@Override
	public void connectToServer(final ServerDevice device) {
		connectThread = new ConnectionToServerEstablisher(serverSocketFactory.createClientSocket(device), connectsToServer);
		connectThread.start();
	}

	@Override
	public void searchServer() {
	}

}
