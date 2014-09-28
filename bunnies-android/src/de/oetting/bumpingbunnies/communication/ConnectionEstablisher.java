package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.ConnectsToServer;
import de.oetting.bumpingbunnies.core.networking.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.init.AcceptThread;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ConnectionEstablisher {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionEstablisher.class);
	private final SocketFactory serverSocketFactory;
	private final AcceptsClientConnections acceptsClientConnections;
	private final ConnectsToServer connectsToServer;
	private ConnectToServerThread connectThread;
	private AcceptThread acceptThread;

	public ConnectionEstablisher(AcceptsClientConnections acceptsClientConnections, ConnectsToServer connectsToServer, SocketFactory serverSocketFactory) {
		this.acceptsClientConnections = acceptsClientConnections;
		this.connectsToServer = connectsToServer;
		this.serverSocketFactory = serverSocketFactory;
		this.acceptThread = null;
		this.connectThread = null;
	}

	public void startThreadToAcceptClients() {
		LOGGER.info("Starting server");
		if (acceptThread != null) {
			this.acceptThread = new AcceptThread(this.serverSocketFactory.create(), this.acceptsClientConnections);
			this.acceptThread.start();
		}
	}

	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		this.acceptThread.stopAcceptingRequests();
		this.acceptThread = null;
		// this.connectThread.close();
		// SocketStorage.getSingleton().closeExistingSocket();
	}

	public void connectToServer(final ServerDevice device) {
		connectThread = new ConnectToServerThread(serverSocketFactory.createClientSocket(device), connectsToServer);
		connectThread.start();
	}

}
