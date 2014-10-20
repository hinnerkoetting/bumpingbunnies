package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class WlanOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, stopper, disconnectCallback);
		return tcpConnection;
	}

	@Override
	public NetworkSender createFastNetworkSender(ThreadErrorCallback stopper, MySocket socket, PlayerDisconnectedCallback disconnectCallback) {
		UdpSocket udpSocket = new UdpSocketFactory().createSendingSocket((TCPSocket) socket, socket.getConnectionIdentifier());
		return SimpleNetworkSenderFactory.createNetworkSender(udpSocket, disconnectCallback);
	}
}
