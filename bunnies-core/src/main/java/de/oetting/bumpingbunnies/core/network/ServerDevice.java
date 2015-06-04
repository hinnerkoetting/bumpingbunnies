package de.oetting.bumpingbunnies.core.network;

public interface ServerDevice {
	MySocket createClientSocket();

	String getName();
	
	boolean canConnectToServer();
}
