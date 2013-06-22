package de.jumpnbump.usecases.start.communication;

import java.io.IOException;

import android.app.Activity;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.networkRoom.ClientConnectedSuccesfullCallback;

public class AcceptThreadImpl extends Thread implements AcceptThread {
	private final ServerSocket mmServerSocket;

	private static final MyLog LOGGER = Logger
			.getLogger(AcceptThreadImpl.class);

	private final Activity activity;

	private final ClientConnectedSuccesfullCallback gameStarter;
	private boolean canceled;

	public AcceptThreadImpl(ServerSocket serverSocket, Activity activity,
			ClientConnectedSuccesfullCallback gameStarter) {
		super("Host thread");
		this.activity = activity;
		this.gameStarter = gameStarter;
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
					this.mmServerSocket.close();
					break;
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
		MyApplication application = (MyApplication) this.activity
				.getApplication();
		application.setSocket(socket);
		this.gameStarter.clientConnectedSucessfull(0);
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