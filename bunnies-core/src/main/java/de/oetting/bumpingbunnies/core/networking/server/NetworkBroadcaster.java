package de.oetting.bumpingbunnies.core.networking.server;

import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class NetworkBroadcaster implements MakesGameVisible {

	private final ThreadErrorCallback errorCallback;

	private SendBroadCastsThread sendBroadcastsThread;
	private ListenForBroadcastsThread broadcastThread;

	public NetworkBroadcaster(ThreadErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	@Override
	public void makeVisible(String name) {
		cancel();
		sendBroadcastsThread = SendBroadcastFactory.create(errorCallback, name);
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

}
