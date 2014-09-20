package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.communication.NetworkSendControl;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		NetworkReceiver receiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl);
		return Arrays.asList(receiveThread);
	}

}
