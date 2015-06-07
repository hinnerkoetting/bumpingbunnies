package de.oetting.bumpingbunnies.core.network;

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

}
