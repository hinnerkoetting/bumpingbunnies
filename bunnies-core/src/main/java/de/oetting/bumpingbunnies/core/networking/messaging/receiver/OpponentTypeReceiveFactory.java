package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface OpponentTypeReceiveFactory {

	List<NetworkReceiver> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl);
}