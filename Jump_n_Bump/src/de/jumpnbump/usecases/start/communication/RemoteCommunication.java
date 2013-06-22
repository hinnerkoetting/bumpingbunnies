package de.jumpnbump.usecases.start.communication;


public interface RemoteCommunication {

	void startServer();

	void closeOpenConnections();

	void connectToServer(ServerDevice device);

	boolean activate();

	void findServer();
}
