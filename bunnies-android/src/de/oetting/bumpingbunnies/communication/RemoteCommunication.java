package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.ServerDevice;

public interface RemoteCommunication {

	void startThreadToAcceptClients();

	void closeOpenConnections();

	void connectToServer(ServerDevice device);

	boolean activate();

	/**
	 * TODO: Temp
	 */
	void findServer(String address);

}
