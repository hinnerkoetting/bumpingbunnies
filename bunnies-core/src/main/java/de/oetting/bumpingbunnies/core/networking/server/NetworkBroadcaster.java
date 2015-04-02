package de.oetting.bumpingbunnies.core.networking.server;

import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class NetworkBroadcaster {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkBroadcaster.class);

	private final ThreadErrorCallback errorCallback;

	private SendBroadCastsThread sendBroadcastsThread;
	private ListenForBroadcastsThread broadcastThread;

	public NetworkBroadcaster(ThreadErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	public void startRegularServerBroadcast(String hostname) {
		cancel();
		sendBroadcastsThread = SendBroadcastFactory.create(errorCallback, hostname);
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

		this.broadcastThread = ListenforBroadCastsThreadFactory.create(callback, errorCallback);
		this.broadcastThread.start();

	}

}
