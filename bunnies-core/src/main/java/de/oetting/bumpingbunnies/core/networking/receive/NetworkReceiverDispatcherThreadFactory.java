package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.parser.GsonFactory;
import de.oetting.bumpingbunnies.core.networking.server.NetworkToOtherClientsDispatcher;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class NetworkReceiverDispatcherThreadFactory {

	public static NetworkReceiveThread createGameNetworkReceiver(MySocket socket, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, ThreadErrorCallback errorCallback) {

		// create a new dispatcher which will dispatch incoming events to all
		// other clients connected to this device.
		NetworkToOtherClientsDispatcher otherClientsDispatcher = new NetworkToOtherClientsDispatcher(socket, networkDispatcher, sendControl);
		return createNetworkReceiver(socket, otherClientsDispatcher, errorCallback);
	}

	public static NetworkReceiver createRoomNetworkReceiver(MySocket socket, PlayerDisconnectedCallback disconnectCallback, ThreadErrorCallback errorCallback) {
		// in the room not all messages are registered wo the dispatcher must
		// not throw exceptions
		NetworkToGameDispatcher networkDispatcher = new EasyNetworkToGameDispatcher(disconnectCallback);
		return createNetworkReceiver(socket, networkDispatcher, errorCallback);
	}

	public static NetworkReceiveThread createNetworkReceiver(MySocket socket, IncomingNetworkDispatcher networkDispatcher, ThreadErrorCallback errorCallback) {
		// always create other clients dispatcher. for clients this will not
		// dispatch incoming events to other sockets
		NetworkReceiveThread thread = new NetworkReceiveThread(new GsonFactory().create(), networkDispatcher, socket, errorCallback);
		return thread;
	}
}
