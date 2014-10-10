package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.FastSocketFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class WlanOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameStopper activity, SocketStorage sockets, PlayerDisconnectedCallback disconnectCallback) {
		Opponent owner = player.getOpponent();
		MySocket socket = sockets.findSocket(owner);
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, activity);
		SimpleNetworkSender udpConnection = createUdpConnection(activity, socket, disconnectCallback);
		return new UdpAndTcpNetworkSender(tcpConnection, udpConnection, owner);
	}

	private SimpleNetworkSender createUdpConnection(GameStopper activity, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		MySocket fastSocket = new FastSocketFactory().createSendingSocket(socket, socket.getOwner());
		return SimpleNetworkSenderFactory.createNetworkSender(fastSocket, disconnectCallback);
	}

}
