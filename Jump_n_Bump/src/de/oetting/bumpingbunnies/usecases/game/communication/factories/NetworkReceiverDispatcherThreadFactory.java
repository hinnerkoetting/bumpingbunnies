package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.util.List;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToOtherClientsDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;

public class NetworkReceiverDispatcherThreadFactory {

	public static NetworkReceiveThread createGameNetworkReceiver(
			MySocket socket, List<? extends ThreadedNetworkSender> allRemoteSender,
			NetworkToGameDispatcher networkDispatcher) {

		// create a new dispatcher which will dispatch incoming events to all
		// other clients connected to this device.
		NetworkToOtherClientsDispatcher otherClientsDispatcher = new NetworkToOtherClientsDispatcher(
				allRemoteSender, socket, networkDispatcher);
		return createNetworkReceiver(socket, otherClientsDispatcher);
	}

	public static NetworkReceiver createRoomNetworkReceiver(MySocket socket) {
		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
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
