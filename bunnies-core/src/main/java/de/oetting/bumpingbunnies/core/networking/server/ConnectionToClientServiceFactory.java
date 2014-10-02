package de.oetting.bumpingbunnies.core.networking.server;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;

public class ConnectionToClientServiceFactory {

	public static ConnectionToClientService create(AcceptsClientConnections origin, MySocket socket, NetworkToGameDispatcher dispatcher) {
		NetworkReceiver receiver = createNetworkReceiver(socket, dispatcher);
		return new ConnectionToClientService(origin, receiver, SocketStorage.getSingleton());
	}

	private static NetworkReceiveThread createNetworkReceiver(MySocket socket, NetworkToGameDispatcher dispatcher) {
		Gson gson = new Gson();
		return new NetworkReceiveThread(gson, dispatcher, socket);
	}
}
