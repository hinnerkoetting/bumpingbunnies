package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameStopper activity, SocketStorage sockets) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		return NetworkSendQueueThreadFactory.create(socket, activity);
	}

}
