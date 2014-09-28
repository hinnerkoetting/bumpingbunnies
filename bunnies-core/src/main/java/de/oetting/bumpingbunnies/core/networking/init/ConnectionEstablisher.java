package de.oetting.bumpingbunnies.core.networking.init;

import de.oetting.bumpingbunnies.core.networking.ServerDevice;

public interface ConnectionEstablisher {

	void startThreadToAcceptClients();

	void closeOpenConnections();

	void connectToServer(ServerDevice device);

	boolean activate();

	void searchServer();

}
