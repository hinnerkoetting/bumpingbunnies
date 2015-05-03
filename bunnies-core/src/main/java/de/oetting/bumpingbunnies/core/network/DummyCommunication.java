package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;

public class DummyCommunication implements ConnectionEstablisher, ClientAccepter {

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
