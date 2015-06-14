package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class NullServerDevice implements ServerDevice {

	@Override
	public MySocket createClientSocket() {
		throw new IllegalArgumentException("Cannot create socket");
	}

	@Override
	public String getName() {
		return "Cannot listen to broadcasts...";
	}

	
	@Override
	public boolean canConnectToServer() {
		return false;
	}

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.UNKNOWN;
	}
}
