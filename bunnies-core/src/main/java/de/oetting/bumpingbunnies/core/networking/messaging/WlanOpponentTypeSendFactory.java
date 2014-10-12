package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.FastSocketFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;

public class WlanOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(GameStopper stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, stopper);
		return tcpConnection;
	}

	private SimpleNetworkSender createUdpConnection(GameStopper activity, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		MySocket fastSocket = new FastSocketFactory().createSendingSocket(socket, socket.getOwner());
		return SimpleNetworkSenderFactory.createNetworkSender(fastSocket, disconnectCallback);
	}

	@Override
	public NetworkSender createFastNetworkSender(GameStopper stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		return SimpleNetworkSenderFactory.createNetworkSender(socket, disconnectCallback);
	}
}
