package de.oetting.bumpingbunnies.usecases.start.communication;

public interface SocketFactory {

	ServerSocket create();

	MySocket createClientSocket(ServerDevice serverDevice);
}
