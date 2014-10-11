package de.oetting.bumpingbunnies.core.networking.server;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class ConnectionToClientServiceFactory {

	public static ToClientConnector create(AcceptsClientConnections origin, MySocket socket, NetworkToGameDispatcher dispatcher,
			PlayerDisconnectedCallback disconnectCallback) {
		NetworkReceiver receiver = createNetworkReceiver(socket, dispatcher);
		return new ToClientConnector(origin, receiver, SocketStorage.getSingleton(), disconnectCallback);
	}

	private static NetworkReceiveThread createNetworkReceiver(MySocket socket, NetworkToGameDispatcher dispatcher) {
		Gson gson = new GsonFactory().create();
		return new NetworkReceiveThread(gson, dispatcher, socket);
	}
}
