package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.core.networking.ServerDevice;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class WlanCommunication implements RemoteCommunication {
	private ConnectionEstablisher commonRemoteCommunication;
	private RoomActivity origin;

	public WlanCommunication(RoomActivity origin, ConnectionEstablisher commonRemoteCommunication) {
		super();
		this.origin = origin;
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
	public void findServer(String address) {
		// TEMP
		try {
			WlanDevice device = new WlanDevice(address);
			this.origin.startConnectToServer(device);
			// this.commonRemoteCommunication.findServer();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
