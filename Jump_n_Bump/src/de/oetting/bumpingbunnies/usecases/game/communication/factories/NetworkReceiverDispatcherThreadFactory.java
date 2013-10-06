package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.EasyNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToOtherClientsDispatcher;

public class NetworkReceiverDispatcherThreadFactory {

	public static NetworkReceiveThread createGameNetworkReceiver(
			MySocket socket,
			NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {

		// create a new dispatcher which will dispatch incoming events to all
		// other clients connected to this device.
		NetworkToOtherClientsDispatcher otherClientsDispatcher = new NetworkToOtherClientsDispatcher(
				socket, networkDispatcher, sendControl);
		return createNetworkReceiver(socket, otherClientsDispatcher);
	}

	public static NetworkReceiver createRoomNetworkReceiver(MySocket socket) {
		// in the room not all messages are registered wo the dispatcher must not throw exceptions
		NetworkToGameDispatcher networkDispatcher = new EasyNetworkToGameDispatcher();
		return createNetworkReceiver(socket, networkDispatcher);
	}

	private static NetworkReceiveThread createNetworkReceiver(MySocket socket,
			IncomingNetworkDispatcher networkDispatcher) {
		// always create other clients dispatcher. for clients this will not
		// dispatch incoming events to other sockets
		NetworkReceiveThread thread = new NetworkReceiveThread(
				new Gson(), networkDispatcher, socket);
		return thread;
	}

}
