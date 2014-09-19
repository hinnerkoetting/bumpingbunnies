package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class WlanOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiveThread> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player,
			NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		List<NetworkReceiveThread> networkReceiveThreads = new ArrayList<NetworkReceiveThread>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket));
		if (socket.isFastSocketPossible()) {
			networkReceiveThreads.add(createFastSocketReceiveThread(networkDispatcher, sendControl, socket));
		}
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createFastSocketReceiveThread(NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl,
			MySocket socket) {
		MySocket fastSocket = new FastSocketFactory().create(socket, socket.getOwner());
		NetworkReceiveThread udpReceiveThread = createNormalSocketNetworkReceiver(networkDispatcher, sendControl, fastSocket);
		return udpReceiveThread;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl, MySocket socket) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory
				.createGameNetworkReceiver(socket,
						networkDispatcher, sendControl);
		return tcpReceiveThread;
	}
}
