package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.networking.ConnectsToServer;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.exceptions.IORuntimeException;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ConnectToServerThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectToServerThread.class);
	private final MySocket mmSocket;
	private final ConnectsToServer activity;

	public ConnectToServerThread(MySocket mmSocket, ConnectsToServer activity) {
		super("Connect to Server thread");
		this.activity = activity;
		this.mmSocket = mmSocket;
	}

	@Override
	public void run() {
		LOGGER.info("Start Client Thread");

		try {
			this.mmSocket.connect();
			this.activity.connectToServerSuccesfull(this.mmSocket);
		} catch (IORuntimeException connectException) {
			this.activity.connectionNotSuccesful(connectException.getMessage());
			LOGGER.warn("Exception during connect to server " + connectException.getMessage());
			LOGGER.warn("Closing connection");
			this.mmSocket.close();
		}
	}

}