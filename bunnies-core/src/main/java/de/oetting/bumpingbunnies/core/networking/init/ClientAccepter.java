package de.oetting.bumpingbunnies.core.networking.init;

public interface ClientAccepter {

	void startThreadToAcceptClients();

	void closeConnections();
}
