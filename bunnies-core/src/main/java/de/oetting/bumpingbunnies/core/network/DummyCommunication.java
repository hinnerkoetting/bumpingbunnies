package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.init.AcceptsClients;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;

public class DummyCommunication implements ConnectionEstablisher, AcceptsClients {

	@Override
	public void startThreadToAcceptClients() {
	}

	@Override
	public void closeConnections() {
	}

	@Override
	public void connectToServer(ServerDevice device) {
	}

	@Override
	public void searchServer() {
	}

}
