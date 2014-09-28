package de.oetting.bumpingbunnies.core.networking.server;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;

public class ConnectionToClientServiceFactory {

	public static ConnectionToClientService create(AcceptsClientConnections origin, MySocket socket, NetworkToGameDispatcher dispatcher) {
		NetworkReceiver receiver = NetworkReceiveThreadFactory.create(socket, dispatcher);
		return new ConnectionToClientService(origin, receiver, SocketStorage.getSingleton());
	}
}
