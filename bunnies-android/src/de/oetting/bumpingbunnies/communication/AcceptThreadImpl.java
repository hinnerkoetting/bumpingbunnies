package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.AcceptsClientConnections;

public class AcceptThreadImpl extends Thread implements AcceptThread {
	private final ServerSocket mmServerSocket;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AcceptThreadImpl.class);

	private final AcceptsClientConnections acceptsConnections;
	private boolean canceled;

	public AcceptThreadImpl(ServerSocket serverSocket,
			AcceptsClientConnections gameStarter) {
		super("Host thread");
		this.acceptsConnections = gameStarter;
		this.mmServerSocket = serverSocket;
	}

	@Override
	public void run() {
		LOGGER.info("Start Server Thread");
		MySocket socket = null;
		// Keep listening until exception occurs or a socket is returned
		while (true) {
			try {
				socket = this.mmServerSocket.accept();

				// If a connection was accepted
				if (socket != null) {
					// Do work to manage the connection (in a separate thread)
					manageConnectedSocket(socket);
				} else {
					LOGGER.info("Socket == null ???");
				}
			} catch (IOException e) {
				if (!this.canceled) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private void manageConnectedSocket(MySocket socket) {
		this.acceptsConnections.clientConnectedSucessfull(socket);
		LOGGER.info("Connection accepeted");
	}

	@Override
	public void close() {
		try {
			this.canceled = true;
			this.mmServerSocket.close();
		} catch (IOException e) {
			LOGGER.warn("Error von close", e);
		}
	}
}