package de.oetting.bumpingbunnies.core.networking.server;

import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
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
			this.broadcastThread.stopListening();
		}
	}

	public void listenForBroadCasts(final OnBroadcastReceived callback) {
		cancel();
		LOGGER.info("Searching for host...");

		this.broadcastThread = ListenforBroadCastsThreadFactory.create(callback);
		this.broadcastThread.start();

	}

}
