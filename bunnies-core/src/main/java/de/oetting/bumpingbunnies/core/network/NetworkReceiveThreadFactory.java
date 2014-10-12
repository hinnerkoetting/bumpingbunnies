package de.oetting.bumpingbunnies.core.network;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverFactory;

public class NetworkReceiveThreadFactory implements NetworkReceiverFactory {

	private final SocketStorage sockets;
	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;
	private final OpponentReceiverFactoryFactory opponenFactoryFactory;

	public NetworkReceiveThreadFactory(SocketStorage sockets, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			OpponentReceiverFactoryFactory opponenFactoryFactory) {
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
		this.opponenFactoryFactory = opponenFactoryFactory;
	}

	@Override
	public List<NetworkReceiver> create(MySocket socket) {
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket));
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			MySocket socket) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl);
		return tcpReceiveThread;
	}
}
