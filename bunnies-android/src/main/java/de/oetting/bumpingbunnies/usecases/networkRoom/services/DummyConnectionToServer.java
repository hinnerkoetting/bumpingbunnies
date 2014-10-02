package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServer;

public class DummyConnectionToServer implements ConnectionToServer {

	@Override
	public void onConnectionToServer() {
	}

	@Override
	public void cancel() {
	}

}
