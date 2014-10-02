package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.core.network.ServerDevice;

public interface ConnectToServerCallback {

	void startConnectToServer(ServerDevice device);
}
