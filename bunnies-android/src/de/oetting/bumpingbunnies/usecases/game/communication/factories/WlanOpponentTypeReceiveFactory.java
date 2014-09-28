package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.FastSocketFactory;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class WlanOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket));
		if (socket.isFastSocketPossible()) {
			networkReceiveThreads.add(createFastSocketReceiveThread(networkDispatcher, sendControl, socket));
		}
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createFastSocketReceiveThread(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, MySocket socket) {
		MySocket fastSocket = new FastSocketFactory().create(socket, socket.getOwner());
		NetworkReceiveThread udpReceiveThread = createNormalSocketNetworkReceiver(networkDispatcher, sendControl, fastSocket);
		return udpReceiveThread;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, MySocket socket) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl);
		return tcpReceiveThread;
	}
}
