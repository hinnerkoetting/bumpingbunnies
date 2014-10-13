package de.oetting.bumpingbunnies.core.networking.server;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class ConnectionToClientServiceFactory {

	public static ToClientConnector create(AcceptsClientConnections origin, MySocket socket, NetworkToGameDispatcher dispatcher,
			PlayerDisconnectedCallback disconnectCallback, ThreadErrorCallback errorCallback) {
		Gson gson = new GsonFactory().create();
		NetworkReceiveThread receiver = new NetworkReceiveThread(gson, dispatcher, socket, errorCallback);
		return new ToClientConnector(origin, receiver, SocketStorage.getSingleton(), disconnectCallback);
	}

}
