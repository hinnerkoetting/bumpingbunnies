package de.oetting.bumpingbunnies.communication;

public interface RemoteCommunication {

	void startServer();

	void closeOpenConnections();

	void connectToServer(ServerDevice device);

	boolean activate();

	/**
	 * TODO: Temp
	 */
	void findServer(String address);

}
