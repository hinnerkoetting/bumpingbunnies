package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AiOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		return new ArrayList<NetworkReceiver>();
	}

}
