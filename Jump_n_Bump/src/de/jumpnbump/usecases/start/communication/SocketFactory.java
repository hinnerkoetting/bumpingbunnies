package de.jumpnbump.usecases.start.communication;

public interface SocketFactory {

	ServerSocket create();

	MySocket createClientSocket(ServerDevice serverDevice);
}
