package de.oetting.bumpingbunnies.core.networking.init;

public interface AcceptsClients {

	void startThreadToAcceptClients();

	void closeConnections();
}
