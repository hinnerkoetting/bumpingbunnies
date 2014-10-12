package de.oetting.bumpingbunnies.core.network.sockets;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.observer.Observable;

public class SocketObservable extends Observable<MySocket> {

	public void socketAdded(MySocket socket) {
		notifyAboutNewEvent(socket);
	}

	public void socketRemoved(MySocket socket) {
		notifyAboutRemoveEvent(socket);
	}
}
