package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.List;

import de.oetting.bumpingbunnies.android.game.SocketStorage;
import de.oetting.bumpingbunnies.communication.NetworkSendControl;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface OpponentTypeReceiveFactory {
	List<NetworkReceiveThread> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl);
}
