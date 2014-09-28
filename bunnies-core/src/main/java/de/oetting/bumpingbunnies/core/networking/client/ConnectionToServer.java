package de.oetting.bumpingbunnies.core.networking.client;

public interface ConnectionToServer {

	void onConnectionToServer();

	void cancel();
}