package de.jumpnbump.usecases.networkRoom;

import de.jumpnbump.usecases.start.communication.ServerDevice;

public interface ConnectToServerCallback {

	void startConnectToServer(ServerDevice device);
}