package de.jumpnbump.usecases;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.start.communication.MySocket;

public class MyApplication extends Application {

	private static final MyLog LOGGER = Logger.getLogger(MyApplication.class);
	private List<MySocket> sockets;

	public MyApplication() {
		this.sockets = new ArrayList<MySocket>();
	}

	@Deprecated
	public MySocket getSocket() {
		return this.sockets.get(0);
	}

	@Deprecated
	public void setSocket(MySocket socket) {
		closeExistingSocket();
		this.sockets.clear();
		this.sockets.add(socket);
	}

	public MySocket getSocket(int index) {
		return this.sockets.get(index);
	}

	public void closeExistingSocket() {
		for (MySocket socket : this.sockets) {
			closeOneSocket(socket);
		}
		this.sockets.clear();
	}

	private void closeOneSocket(MySocket socket) {
		try {
			LOGGER.info("close connection");
			socket.close();
		} catch (Exception e) {
			LOGGER.warn("Exception during closing socket: %s", e.getMessage());
		}
	}

}
