package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.WlanOpponentTypeReceiveFactory;

public class NetworkReceiveControlFactory {

	public static NetworkReceiveControl create(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			OpponentReceiverFactoryFactory opponentTypeReceiveFactoryFactory) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl,
				opponentTypeReceiveFactoryFactory);

		List<NetworkReceiver> udpReceiverThreads = createReceiverThreads(networkDispatcher, sendControl);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory, udpReceiverThreads);
		return receiveControl;
	}

	private static List<NetworkReceiver> createReceiverThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		return new WlanOpponentTypeReceiveFactory().createReceivingThreads(networkDispatcher, sendControl);
	}
}
