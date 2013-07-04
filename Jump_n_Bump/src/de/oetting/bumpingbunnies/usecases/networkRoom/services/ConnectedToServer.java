package de.oetting.bumpingbunnies.usecases.networkRoom.services;

public interface ConnectedToServer {

	void onConnectionToServer();

	void cancel();
}