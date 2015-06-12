package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.init.DeviceDiscovery;

public class DummyCommunication implements DeviceDiscovery, ClientAccepter {

	@Override
	public void startThreadToAcceptClients() {
	}

	@Override
	public void closeConnections() {
	}

	@Override
	public void searchServer() {
	}

}
