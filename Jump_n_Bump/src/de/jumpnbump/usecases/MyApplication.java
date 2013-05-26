package de.jumpnbump.usecases;

import java.io.IOException;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class MyApplication extends Application {

	private static final MyLog LOGGER = Logger.getLogger(MyApplication.class);
	private BluetoothSocket socket;

	public BluetoothSocket getSocket() {
		return this.socket;
	}

	public void setSocket(BluetoothSocket socket) {
		closeExistingSocket();
		this.socket = socket;
	}

	public void closeExistingSocket() {
		try {
			if (this.socket != null) {
				LOGGER.info("close connection");
				this.socket.close();
			}
		} catch (IOException e) {
			LOGGER.warn("Exception during connection close");
		}
	}

}
