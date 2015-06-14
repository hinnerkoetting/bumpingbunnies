package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public interface ServerDevice {
	MySocket createClientSocket();

	String getName();
	
	boolean canConnectToServer();
	
	NetworkType getNetworkType();
	
}
