package de.oetting.bumpingbunnies.core.network;

public class NullServerDevice implements ServerDevice {

	@Override
	public MySocket createClientSocket() {
		throw new IllegalArgumentException("Cannot create socket");
	}

	@Override
	public String getName() {
		return "Cannot listen to broadcasts...";
	}

}
