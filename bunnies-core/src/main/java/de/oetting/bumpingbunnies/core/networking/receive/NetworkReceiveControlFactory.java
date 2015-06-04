package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket.UdpException;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NetworkReceiveControlFactory {

	public static NetworkReceiveControl create(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, Configuration configuration,
			ThreadErrorCallback errorCallback, World world) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(networkDispatcher, sendControl, errorCallback, world);

		List<NetworkReceiver> allThreads = createReceiverThreadsForExistingPlayers(networkDispatcher, sendControl, configuration, errorCallback);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory, allThreads);
		return receiveControl;
	}

	private static List<NetworkReceiver> createReceiverThreadsForExistingPlayers(NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, Configuration configuration, ThreadErrorCallback errorCallback) {
		try {
			List<NetworkReceiver> udpReceiverThreads = createUdpReceiverThreads(networkDispatcher, sendControl, configuration, errorCallback);
			List<NetworkReceiver> allThreads = new ArrayList<NetworkReceiver>();
			allThreads.addAll(udpReceiverThreads);
			return allThreads;
		} catch (UdpException e) {
			throw new CouldNotCreateListeningSocketsException("UDP", e.getPort(), e);
		}
	}

	private static List<NetworkReceiver> createUdpReceiverThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			Configuration configuration, ThreadErrorCallback errorCallback) {
		if (configuration.isHost()) {
			return new WlanOpponentTypeReceiveFactory().createListeningForUpdpThreads(networkDispatcher, sendControl, NetworkConstants.SERVER_NETWORK_PORT,
					errorCallback);
		} else {
			MySocket serversocket = SocketStorage.getSingleton().getSocket(0);
			if (serversocket instanceof TCPSocket) {
				TCPSocket tcpSocket = (TCPSocket) serversocket;
				return new WlanOpponentTypeReceiveFactory().createListeningForUpdpThreads(networkDispatcher, sendControl, tcpSocket.getSocketSettings()
						.getLocalPort(), errorCallback);
			}
			return new ArrayList<NetworkReceiver>();
		}
	}
}
