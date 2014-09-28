package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class AcceptThreadImpl extends Thread implements AcceptThread {
	private final ServerSocket mmServerSocket;

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptThreadImpl.class);

	private final AcceptsClientConnections acceptsConnections;
	private boolean canceled;

	public AcceptThreadImpl(ServerSocket serverSocket, AcceptsClientConnections gameStarter) {
		super("Accepts requests from Clients");
		this.acceptsConnections = gameStarter;
		this.mmServerSocket = serverSocket;
	}

	@Override
	public void run() {
		LOGGER.info("Start Server Thread");
		while (true) {
			try {
				MySocket socket = this.mmServerSocket.accept();
				manageConnectedSocket(socket);
			} catch (IOException e) {
				if (!this.canceled) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private void manageConnectedSocket(MySocket socket) {
		this.acceptsConnections.clientConnectedSucessfull(socket);
		LOGGER.info("Connection accepted");
	}

	@Override
	public void close() {
		try {
			this.canceled = true;
			this.mmServerSocket.close();
		} catch (IOException e) {
			LOGGER.warn("Error on close", e);
		}
	}
}