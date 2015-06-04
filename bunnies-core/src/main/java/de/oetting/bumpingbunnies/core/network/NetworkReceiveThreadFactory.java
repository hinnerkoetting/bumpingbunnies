package de.oetting.bumpingbunnies.core.network;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NetworkReceiveThreadFactory {

	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;
	private final ThreadErrorCallback errorCallback;
	private final World world;

	public NetworkReceiveThreadFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, ThreadErrorCallback errorCallback,
			World world) {
		this.networkDispatcher = networkDispatcher;
		this.sendControl = sendControl;
		this.errorCallback = errorCallback;
		this.world = world;
	}

	public List<NetworkReceiver> create(MySocket socket) {
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket, errorCallback));
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			MySocket socket, ThreadErrorCallback errorCallback) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl,
				errorCallback, world);
		return tcpReceiveThread;
	}
}
