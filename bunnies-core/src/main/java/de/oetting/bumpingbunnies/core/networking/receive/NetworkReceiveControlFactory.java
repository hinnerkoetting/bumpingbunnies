package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.configuration.Configuration;

public class NetworkReceiveControlFactory {

	public static NetworkReceiveControl create(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			OpponentReceiverFactoryFactory opponentTypeReceiveFactoryFactory, Configuration configuration) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl,
				opponentTypeReceiveFactoryFactory);

		List<NetworkReceiver> udpReceiverThreads = createReceiverThreads(networkDispatcher, sendControl, configuration);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory, udpReceiverThreads);
		return receiveControl;
	}

	private static List<NetworkReceiver> createReceiverThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			Configuration configuration) {
		if (configuration.isHost()) {
			return new WlanOpponentTypeReceiveFactory().createReceivingThreads(networkDispatcher, sendControl, NetworkConstants.SERVER_NETWORK_PORT);
		} else {
			MySocket serversocket = SocketStorage.getSingleton().getSocket(0);
			if (serversocket instanceof TCPSocket) {
				TCPSocket tcpSocket = (TCPSocket) serversocket;
				return new WlanOpponentTypeReceiveFactory()
						.createReceivingThreads(networkDispatcher, sendControl, tcpSocket.getSocketSettings().getLocalPort());
			}
			return new ArrayList<NetworkReceiver>();
		}
	}
}
