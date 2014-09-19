package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiveThread> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		NetworkReceiveThread receiveThread = NetworkReceiverDispatcherThreadFactory
				.createGameNetworkReceiver(socket,
						networkDispatcher, sendControl);
		return Arrays.asList(receiveThread);
	}

}
