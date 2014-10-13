package de.oetting.bumpingbunnies.core.network;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class NetworkReceiveThreadFactory {

	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;
	private final ThreadErrorCallback errorCallback;

	public NetworkReceiveThreadFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, ThreadErrorCallback errorCallback) {
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
		this.errorCallback = errorCallback;
	}

	public List<NetworkReceiver> create(MySocket socket) {
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket, errorCallback));
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			MySocket socket, ThreadErrorCallback errorCallback) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl,
				errorCallback);
		return tcpReceiveThread;
	}
}
