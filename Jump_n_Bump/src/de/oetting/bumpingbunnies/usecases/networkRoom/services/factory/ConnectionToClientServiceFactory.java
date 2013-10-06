package de.oetting.bumpingbunnies.usecases.networkRoom.services.factory;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToClientService;

public class ConnectionToClientServiceFactory {

	public static ConnectionToClientService create(RoomActivity origin,
			MySocket socket, NetworkToGameDispatcher dispatcher) {
		NetworkReceiver receiver = NetworkReceiveThreadFactory.create(socket,
				dispatcher);
		return new ConnectionToClientService(origin, receiver, SocketStorage.getSingleton());
	}
}
