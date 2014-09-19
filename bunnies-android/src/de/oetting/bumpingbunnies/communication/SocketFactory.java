package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public interface SocketFactory {

	ServerSocket create();

	MySocket createClientSocket(ServerDevice serverDevice);
}
