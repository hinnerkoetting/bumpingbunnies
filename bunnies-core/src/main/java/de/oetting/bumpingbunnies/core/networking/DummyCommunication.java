package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;

public class DummyCommunication implements ConnectionEstablisher {

	@Override
	public void startThreadToAcceptClients() {
	}

	@Override
	public void closeOpenConnections() {
	}

	@Override
	public void connectToServer(ServerDevice device) {
	}

	@Override
	public boolean activate() {
		return false;
	}

	@Override
	public void searchServer() {
	}

}
