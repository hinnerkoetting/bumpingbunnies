package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public class SearchingServerDevice implements ServerDevice {

	private final String name;

	public SearchingServerDevice(String name) {
		this.name = name;
	}

	@Override
	public MySocket createClientSocket() {
		throw new IllegalArgumentException();
	}

	@Override
	public String getName() {
		return name;
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
