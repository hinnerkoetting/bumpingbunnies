package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveThread;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class WlanOpponentTypeReceiveFactory implements OpponentTypeReceiveFactory {

	@Override
	public List<NetworkReceiver> createReceiveThreadsForOnePlayer(SocketStorage sockets, Player player, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		MySocket socket = sockets.findSocket(player.getOpponent());
		List<NetworkReceiver> networkReceiveThreads = new ArrayList<NetworkReceiver>();
		networkReceiveThreads.add(createNormalSocketNetworkReceiver(networkDispatcher, sendControl, socket));
		return networkReceiveThreads;
	}

	private NetworkReceiveThread createNormalSocketNetworkReceiver(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			MySocket socket) {
		NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory.createGameNetworkReceiver(socket, networkDispatcher, sendControl);
		return tcpReceiveThread;
	}

	public List<NetworkReceiver> createReceivingThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		UdpSocketSettings socketSettings = new UdpSocketSettings(null, NetworkConstants.UDP_PORT, -1);
		UdpSocket listeningSocket = new UdpSocketFactory().createListeningSocket(socketSettings);
		return Arrays.asList((NetworkReceiver) createNormalSocketNetworkReceiver(networkDispatcher, sendControl, listeningSocket));
	}

}
