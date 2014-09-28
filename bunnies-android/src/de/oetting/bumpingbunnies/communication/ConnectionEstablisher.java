package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.ConnectsToServer;

public class ConnectionEstablisher implements RemoteCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionEstablisher.class);
	private final SocketFactory serverSocketFactory;
	private final AcceptsClientConnections acceptsClientConnections;
	private final ConnectsToServer connectsToServer;
	private ConnectThread connectThread;
	private AcceptThread acceptThread;

	public ConnectionEstablisher(AcceptsClientConnections acceptsClientConnections, ConnectsToServer connectsToServer, SocketFactory serverSocketFactory) {
		this.acceptsClientConnections = acceptsClientConnections;
		this.connectsToServer = connectsToServer;
		this.serverSocketFactory = serverSocketFactory;
		this.acceptThread = new DummyAcceptThread();
		this.connectThread = new DummyConnectThread();
	}

	@Override
	public void startServer() {
		LOGGER.info("Starting server");
		closeOpenConnections();
		this.acceptThread = new AcceptThreadImpl(this.serverSocketFactory.create(), this.acceptsClientConnections);
		this.acceptThread.start();
	}

	@Override
	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		this.acceptThread.close();
		// this.connectThread.close();
		// SocketStorage.getSingleton().closeExistingSocket();
	}

	@Override
	public void connectToServer(final ServerDevice device) {
		ConnectionEstablisher.this.connectThread = new ConnectThreadImpl(ConnectionEstablisher.this.serverSocketFactory.createClientSocket(device),
				ConnectionEstablisher.this.connectsToServer);
		ConnectionEstablisher.this.connectThread.start();
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void findServer(String address) {
	}

}
