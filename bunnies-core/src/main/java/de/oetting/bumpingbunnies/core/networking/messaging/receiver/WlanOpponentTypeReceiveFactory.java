package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class WlanOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(MySocket socket, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket));
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			MySocket socket) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl);
		return tcpReceiveThread;
	}

	/**
	 * Creates a thead which listens on the udp port for player state messages.
	 */
	public List<NetworkReceiver> createListeningForUpdpThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, int localPort) {
		UdpSocketSettings socketSettings = new UdpSocketSettings(null, localPort, -1);
		UdpSocket listeningSocket = new UdpSocketFactory().createListeningSocket(socketSettings);
		// UDP messages are not directly distributed to other clients.
		NetworkReceiver networkReceiver = NetworkReceiverDispatcherThreadFactory.createNetworkReceiver(listeningSocket, networkDispatcher);
		networkReceiver.start();
		return Arrays.asList(networkReceiver);
	}

}
