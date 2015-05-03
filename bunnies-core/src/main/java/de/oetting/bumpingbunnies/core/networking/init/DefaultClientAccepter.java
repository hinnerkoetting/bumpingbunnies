package de.oetting.bumpingbunnies.core.networking.init;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class DefaultClientAccepter implements ClientAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClientAccepter.class);

	private final SocketFactory serverSocketFactory;
	private final AcceptsClientConnections acceptsClientConnections;
	private final ThreadErrorCallback threadErrorCallback;
	private AcceptConnectionsOnSocketThread acceptThread;

	public DefaultClientAccepter(SocketFactory serverSocketFactory, AcceptsClientConnections acceptsClientConnections, ThreadErrorCallback threadErrorCallback) {
		this.serverSocketFactory = serverSocketFactory;
		this.acceptsClientConnections = acceptsClientConnections;
		this.threadErrorCallback = threadErrorCallback;
	}

	@Override
	public void startThreadToAcceptClients() {
		LOGGER.info("Starting server");
		if (acceptThread == null) {
			this.acceptThread = new AcceptConnectionsOnSocketThread(this.serverSocketFactory.create(), this.acceptsClientConnections, threadErrorCallback);
			this.acceptThread.start();
		}
	}

	@Override
	public void closeConnections() {
		LOGGER.info("Closing connections");
		if (acceptThread != null)
			this.acceptThread.stopAcceptingRequests();
		this.acceptThread = null;
	}

}
