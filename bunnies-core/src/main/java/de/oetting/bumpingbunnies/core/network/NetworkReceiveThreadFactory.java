package de.oetting.bumpingbunnies.core.network;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverFactory;

public class NetworkReceiveThreadFactory implements NetworkReceiverFactory {

	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;

	public NetworkReceiveThreadFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
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
