package de.oetting.bumpingbunnies.usecases.game.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class SocketStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketStorage.class);

	private static SocketStorage singleton;
	private List<MySocket> sockets;

	public static SocketStorage getSingleton() {
		if (singleton == null) {
			singleton = new SocketStorage();
		}
		return singleton;
	}

	public SocketStorage() {
		this.sockets = new ArrayList<MySocket>();
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

	public MySocket findSocket(Opponent opponent) {
		for (MySocket s : this.sockets) {
			if (s.getOwner().equals(opponent)) {
				return s;
			}
		}
		throw new OpponentDoesNotExist();
	}

	private void closeOneSocket(MySocket socket) {
		try {
			LOGGER.info("close connection");
			socket.close();
		} catch (Exception e) {
			LOGGER.warn("Exception during closing socket: %s", e.getMessage());
		}
	}

	public int addSocket(MySocket socket) {
		int newPosition = this.sockets.size();
		this.sockets.add(socket);
		return newPosition;
	}

	public List<MySocket> getAllSockets() {
		return Collections.unmodifiableList(this.sockets);
	}

	public static class OpponentDoesNotExist extends RuntimeException {
	}
}
