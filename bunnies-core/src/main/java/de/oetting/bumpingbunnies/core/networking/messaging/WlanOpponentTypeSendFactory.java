package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.FastSocketFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class WlanOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameStopper activity, SocketStorage sockets) {
		Opponent owner = player.getOpponent();
		MySocket socket = sockets.findSocket(owner);
		de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, activity);
		NetworkSendQueueThread udpConnection = createUdpConnection(activity, socket);
		return new UdpAndTcpNetworkSender(tcpConnection, udpConnection, owner);
	}

	private NetworkSendQueueThread createUdpConnection(GameStopper activity, MySocket socket) {
		MySocket fastSocket = new FastSocketFactory().create(socket, socket.getOwner());
		return NetworkSendQueueThreadFactory.create(fastSocket, activity);
	}

}
