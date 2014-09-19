package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.communication.ServerDevice;

public interface ConnectToServerCallback {

	void startConnectToServer(ServerDevice device);
}
