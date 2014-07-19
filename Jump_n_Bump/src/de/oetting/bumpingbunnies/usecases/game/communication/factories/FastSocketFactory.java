package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.UdpSocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class FastSocketFactory {

	public MySocket create(MySocket socket, Opponent owner) {
		if (socket instanceof TCPSocket) {
			return UdpSocketFactory.singleton().create((TCPSocket) socket, owner);
		}
		throw new FastSocketNotPossible(socket.getClass());
	}

	public class FastSocketNotPossible extends RuntimeException {

		public FastSocketNotPossible(Class<?> clazz) {
			super(clazz.getSimpleName() + "does not have a fast option");
		}
	}
}
