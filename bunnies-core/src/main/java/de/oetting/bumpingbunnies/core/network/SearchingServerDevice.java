package de.oetting.bumpingbunnies.core.network;

public class SearchingServerDevice implements ServerDevice{

	@Override
	public MySocket createClientSocket() {
		throw new IllegalArgumentException();
	}

	@Override
	public String getName() {
		return "Searching...";
	}

	@Override
	public boolean canConnectToServer() {
		return false;
	}

}
