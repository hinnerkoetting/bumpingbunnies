package de.oetting.bumpingbunnies.core.networking.receive;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;

public class AiOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(MySocket socket, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, ThreadErrorCallback errorCallback, World world) {
		return new ArrayList<NetworkReceiver>();
	}

}
