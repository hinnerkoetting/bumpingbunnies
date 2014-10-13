package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class BluetoothOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(MySocket socket, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, ThreadErrorCallback errorCallback) {
		NetworkReceiver receiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl, errorCallback);
		return Arrays.asList(receiveThread);
	}

}
