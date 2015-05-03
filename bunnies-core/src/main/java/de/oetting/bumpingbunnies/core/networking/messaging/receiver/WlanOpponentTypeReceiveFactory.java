package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class WlanOpponentTypeReceiveFactory {

	/**
	 * Creates a thead which listens on the udp port for player state messages.
	 */
	public List<NetworkReceiver> createListeningForUpdpThreads(NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, int localPort, ThreadErrorCallback errorCallback) {
		UdpSocketSettings socketSettings = new UdpSocketSettings(null, localPort, -1);
		UdpSocket listeningSocket = new UdpSocketFactory().createListeningSocket(socketSettings);
		// UDP messages are not directly distributed to other clients.
		NetworkReceiver networkReceiver = NetworkReceiverDispatcherThreadFactory.createNetworkReceiver(listeningSocket,
				networkDispatcher, errorCallback);
		return Arrays.asList(networkReceiver);
	}

}
