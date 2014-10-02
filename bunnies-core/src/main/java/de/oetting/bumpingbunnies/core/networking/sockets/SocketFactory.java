package de.oetting.bumpingbunnies.core.networking.sockets;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.ServerDevice;

public interface SocketFactory {

	ServerSocket create();

	MySocket createClientSocket(ServerDevice serverDevice);
}
