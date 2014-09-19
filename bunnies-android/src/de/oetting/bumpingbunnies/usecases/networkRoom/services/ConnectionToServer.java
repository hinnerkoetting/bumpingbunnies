package de.oetting.bumpingbunnies.usecases.networkRoom.services;

public interface ConnectionToServer {

	void onConnectionToServer();

	void cancel();
}