package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.network.ConnectsToServer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.exceptions.IORuntimeException;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ToServerConnector extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ToServerConnector.class);
	private final MySocket mmSocket;
	private final ConnectsToServer activity;

	public ToServerConnector(MySocket mmSocket, ConnectsToServer connectToServerCallback) {
		super("Connect to Server thread");
		this.activity = connectToServerCallback;
		this.mmSocket = mmSocket;
		setDaemon(true);
	}

	@Override
	public void run() {
		LOGGER.info("Start Client Thread");

		try {
			this.mmSocket.connect();
			this.activity.connectToServerSuccesfull(this.mmSocket);
		} catch (IORuntimeException connectException) {
			LOGGER.warn("Exception during connect to server " + connectException.getMessage());
			LOGGER.warn("Closing connection");
			this.activity.connectionNotSuccesful(connectException.getMessage());
			this.mmSocket.close();
		}
	}

}