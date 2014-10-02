package de.oetting.bumpingbunnies.core.networking.server;

import java.net.DatagramSocket;
import java.net.SocketException;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class NetworkBroadcaster {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkBroadcaster.class);
	private SendBroadCastsThread sendBroadcastsThread;
	private ListenForBroadcastsThread broadcastThread;

	public void startRegularServerBroadcast() {
		cancel();
		sendBroadcastsThread = SendBroadcastFactory.create();
		sendBroadcastsThread.start();
	}

	public void cancel() {
		if (this.sendBroadcastsThread != null) {
			this.sendBroadcastsThread.cancel();
			this.sendBroadcastsThread.closeAllSockets();
		}
		if (this.broadcastThread != null) {
			this.broadcastThread.cancel();
			this.broadcastThread.closeSocket();
		}
	}

	public void listenForBroadCasts(final OnBroadcastReceived room) throws SocketException {
		cancel();
		LOGGER.info("Searching for host...");
		DatagramSocket socket = new DatagramSocket(NetworkConstants.BROADCAST_PORT);

		NetworkBroadcaster.this.broadcastThread = new ListenForBroadcastsThread(socket, room);
		NetworkBroadcaster.this.broadcastThread.start();

	}

}
