package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class ConnectThreadImpl extends Thread implements ConnectThread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectThreadImpl.class);
	private final MySocket mmSocket;
	private final RoomActivity activity;

	public ConnectThreadImpl(MySocket mmSocket, RoomActivity activity) {
		super("Connect to Server thread");
		this.activity = activity;
		// Use a temporary object that is later assigned to mmSocket,
		// because mmSocket is final

		this.mmSocket = mmSocket;
	}

	@Override
	public void run() {
		LOGGER.info("Start Client Thread");

		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			this.mmSocket.connect();
		} catch (IOException connectException) {
			this.activity.connectionNotSuccesful(connectException.getMessage());
			LOGGER.warn("Exception during connect to server "
					+ connectException.getMessage());
			// Unable to connect; close the socket and get out
			try {
				LOGGER.warn("Closing connection");
				this.mmSocket.close();
			} catch (IOException closeException) {
			}
			return;
		}

		// Do work to manage the connection (in a separate thread)
		manageConnectedSocket();
	}

	private void manageConnectedSocket() {

		this.activity.connectToServerSuccesfull(this.mmSocket);
		LOGGER.info("Connection accepted");
	}

	@Override
	public void close() {
		try {
			this.mmSocket.close();
		} catch (IOException e) {
			LOGGER.warn("Error von close", e);
		}
	}
}