package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.exceptions.IORuntimeException;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ConnectionToServerEstablisher extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionToServerEstablisher.class);
	private final MySocket mmSocket;
	private final ConnectsToServer callback;

	public ConnectionToServerEstablisher(MySocket mmSocket, ConnectsToServer connectToServerCallback) {
		super("Connect to Server thread");
		this.callback = connectToServerCallback;
		this.mmSocket = mmSocket;
		setDaemon(true);
	}

	@Override
	public void run() {
		LOGGER.info("Start Client Thread");

		try {
			this.mmSocket.connect();
			this.callback.connectToServerSuccesfull(this.mmSocket);
		} catch (IORuntimeException connectException) {
			LOGGER.error("Exception during connect to server " + connectException.getMessage(), connectException);
			LOGGER.warn("Closing connection");
			this.callback.connectionNotSuccesful(connectException.getMessage());
			this.mmSocket.close();
		}
	}

}