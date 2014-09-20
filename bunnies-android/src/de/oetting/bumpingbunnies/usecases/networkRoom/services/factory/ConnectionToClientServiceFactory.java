package de.oetting.bumpingbunnies.usecases.networkRoom.services.factory;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToClientService;

public class ConnectionToClientServiceFactory {

	public static ConnectionToClientService create(AcceptsClientConnections origin,
			MySocket socket, NetworkToGameDispatcher dispatcher) {
		NetworkReceiver receiver = NetworkReceiveThreadFactory.create(socket,
				dispatcher);
		return new ConnectionToClientService(origin, receiver, SocketStorage.getSingleton());
	}
}
