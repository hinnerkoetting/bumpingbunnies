package de.oetting.bumpingbunnies.core.networking.init;

import de.oetting.bumpingbunnies.core.network.ServerDevice;

public interface ConnectionEstablisher {

	void closeConnections();

	void connectToServer(ServerDevice device);

	void searchServer();

}
