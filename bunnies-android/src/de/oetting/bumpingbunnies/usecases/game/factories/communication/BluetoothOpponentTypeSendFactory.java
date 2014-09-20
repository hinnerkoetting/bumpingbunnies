package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.game.SocketStorage;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameActivity activity, SocketStorage sockets) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		return NetworkSendQueueThreadFactory.create(socket, activity);
	}

}
