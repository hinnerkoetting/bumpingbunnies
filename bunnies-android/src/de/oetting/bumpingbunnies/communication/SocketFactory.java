package de.oetting.bumpingbunnies.communication;

public interface SocketFactory {

	ServerSocket create();

	MySocket createClientSocket(ServerDevice serverDevice);
}
