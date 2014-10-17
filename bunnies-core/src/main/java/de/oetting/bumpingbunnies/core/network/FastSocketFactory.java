package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class FastSocketFactory {

	public MySocket createListeningSocket(MySocket socket, ConnectionIdentifier owner) {
		if (socket instanceof TCPSocket) {
			return new UdpSocketFactory().createListeningSocket((TCPSocket) socket, owner);
		}
		throw new FastSocketNotPossible(socket.getClass());
	}

	public MySocket createSendingSocket(MySocket socket, ConnectionIdentifier owner) {
		if (socket instanceof TCPSocket) {
			return new UdpSocketFactory().createSendingSocket((TCPSocket) socket, owner);
		}
		throw new FastSocketNotPossible(socket.getClass());
	}

	public class FastSocketNotPossible extends RuntimeException {

		public FastSocketNotPossible(Class<?> clazz) {
			super(clazz.getSimpleName() + "does not have a fast option");
		}
	}
}
