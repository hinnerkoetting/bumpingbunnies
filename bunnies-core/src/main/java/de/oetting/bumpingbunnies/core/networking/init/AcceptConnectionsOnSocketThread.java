package de.oetting.bumpingbunnies.core.networking.init;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class AcceptConnectionsOnSocketThread extends BunniesThread {

	private final ServerSocket listeningSocket;

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptConnectionsOnSocketThread.class);

	private final AcceptsClientConnections acceptsConnections;
	private boolean canceled;

	public AcceptConnectionsOnSocketThread(ServerSocket serverSocket, AcceptsClientConnections gameStarter, ThreadErrorCallback onErrorCallback) {
		super("Accepts requests from Clients", onErrorCallback);
		this.acceptsConnections = gameStarter;
		this.listeningSocket = serverSocket;
	}

	@Override
	protected void doRun() throws IOException {
		while (!canceled) {
			try {
				MySocket socket = this.listeningSocket.accept();
				callSuccess(socket);
			} catch (IOException e) {
				throwIfNotCancelled(e);
			}
		}
	}

	private void callSuccess(MySocket socket) {
		this.acceptsConnections.clientConnectedSucessfull(socket);
		LOGGER.info("Connection accepted");
	}

	private void throwIfNotCancelled(IOException e) throws IOException {
		if (!this.canceled) {
			throw e;
		}
	}

	public void stopAcceptingRequests() {
		try {
			this.canceled = true;
			this.listeningSocket.close();
		} catch (IOException e) {
			LOGGER.warn("Error while closing socket for new clients. Continuing...", e);
		}
	}
}