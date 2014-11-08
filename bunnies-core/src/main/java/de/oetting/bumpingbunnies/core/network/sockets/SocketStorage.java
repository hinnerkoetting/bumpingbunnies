package de.oetting.bumpingbunnies.core.network.sockets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.observer.Observer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class SocketStorage implements Observer<Player> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketStorage.class);

	private static SocketStorage singleton;
	private List<MySocket> sockets;

	private SocketObservable observable = new SocketObservable();

	public static SocketStorage getSingleton() {
		if (singleton == null) {
			synchronized (SocketStorage.class) {
				if (singleton == null)
					singleton = new SocketStorage();
			}
		}
		return singleton;
	}

	public SocketStorage() {
		this.sockets = new ArrayList<MySocket>();
	}

	public MySocket getSocket(int index) {
		return this.sockets.get(index);
	}

	public synchronized void closeExistingSockets() {
		for (MySocket socket : this.sockets) {
			closeOneSocket(socket);
		}
		this.sockets.clear();
	}

	public synchronized MySocket findSocket(ConnectionIdentifier opponent) {
		MySocket socket = findSocketOrNull(opponent);
		if (socket == null) {
			throw new OpponentDoesNotExist(opponent);
		} else {
			return socket;
		}
	}

	private void closeOneSocket(MySocket socket) {
		try {
			LOGGER.info("closing one socket");
			socket.close();
		} catch (Exception e) {
			LOGGER.warn("Exception during closing socket: %s", e.getMessage());
		}
	}

	public synchronized int addSocket(MySocket socket) {
		int newPosition = this.sockets.size();
		this.sockets.add(socket);
		observable.socketAdded(socket);
		return newPosition;
	}

	public List<MySocket> getAllSockets() {
		return Collections.unmodifiableList(this.sockets);
	}

	public boolean existsSocket(ConnectionIdentifier opponent) {
		return findSocketOrNull(opponent) != null;
	}

	private MySocket findSocketOrNull(ConnectionIdentifier opponent) {
		for (MySocket s : this.sockets) {
			if (s.getConnectionIdentifier().equals(opponent)) {
				return s;
			}
		}
		return null;
	}

	public synchronized void removeSocket(MySocket socket) {
		closeOneSocket(socket);
		sockets.remove(socket);
		observable.socketRemoved(socket);
	}

	public void addObserver(Observer<MySocket> observer) {
		observable.addListener(observer);
	}

	public synchronized void notifyListenersAboutExistingSockets() {
		for (MySocket socket : sockets)
			observable.socketAdded(socket);
	}

	public synchronized void removeListeners() {
		observable.removeListeners();
	}

	public static class OpponentDoesNotExist extends RuntimeException {

		public OpponentDoesNotExist(ConnectionIdentifier opponent) {
			super("Opponentidentifier = " + opponent.getIdentifier());
		}
	}

	@Override
	public void newEvent(Player object) {
	}

	@Override
	public synchronized void removeEvent(Player object) {
		if (object.getOpponent().isDirectlyConnected())
			removeSocket(object);
	}

	private void removeSocket(Player object) {
		MySocket socket = findSocket(object.getOpponent());
		removeSocket(socket);
	}
}
