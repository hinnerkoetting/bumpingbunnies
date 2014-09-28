package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.core.networking.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;

public class WlanCommunication implements RemoteCommunication {

	private ConnectionEstablisher commonRemoteCommunication;

	public WlanCommunication(ConnectionEstablisher commonRemoteCommunication) {
		this.commonRemoteCommunication = commonRemoteCommunication;
	}

	@Override
	public void startThreadToAcceptClients() {
		this.commonRemoteCommunication.startThreadToAcceptClients();
	}

	@Override
	public void closeOpenConnections() {
		this.commonRemoteCommunication.closeOpenConnections();
	}

	@Override
	public void connectToServer(ServerDevice device) {
		this.commonRemoteCommunication.connectToServer(device);
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void searchServer() {
	}
}
