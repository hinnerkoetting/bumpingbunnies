package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public interface OpponentTypeReceiveFactory {

	List<NetworkReceiver> createReceiveThreadsForOnePlayer(MySocket socket, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			ThreadErrorCallback errorCallback);
}
