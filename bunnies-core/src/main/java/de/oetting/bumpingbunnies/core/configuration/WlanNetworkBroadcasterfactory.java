package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class WlanNetworkBroadcasterfactory implements MakesGameVisibleFactory{

	@Override
	public NetworkType forNetworkType() {
		return NetworkType.WLAN;
	}

	@Override
	public NetworkBroadcaster create(ThreadErrorCallback callback) {
		return new NetworkBroadcaster(callback);
	}

}
