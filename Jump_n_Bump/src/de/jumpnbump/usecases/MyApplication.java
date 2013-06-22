package de.jumpnbump.usecases;

import java.io.IOException;

import android.app.Application;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.start.communication.MySocket;

public class MyApplication extends Application {

	private static final MyLog LOGGER = Logger.getLogger(MyApplication.class);
	private MySocket socket;

	public MySocket getSocket() {
		return this.socket;
	}

	public void setSocket(MySocket socket) {
		closeExistingSocket();
		this.socket = socket;
	}

	public void closeExistingSocket() {
		try {
			if (this.socket != null) {
				LOGGER.info("close connection");
				this.socket.close();
				this.socket = null;
			}
		} catch (IOException e) {
			LOGGER.warn("Exception during connection close");
		}
	}

}
