package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;

public class NetworkReceiveControlFactory {

	public static NetworkReceiveControl create(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			OpponentReceiverFactoryFactory opponentTypeReceiveFactoryFactory) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl,
				opponentTypeReceiveFactoryFactory);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}
}
