package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.communication.RemoteCommunication;
import de.oetting.bumpingbunnies.communication.RemoteCommunicationImpl;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class WlanCommunication implements RemoteCommunication {
	private RemoteCommunicationImpl commonRemoteCommunication;
	private RoomActivity origin;

	public WlanCommunication(RoomActivity origin,
			RemoteCommunicationImpl commonRemoteCommunication) {
		super();
		this.origin = origin;
		this.commonRemoteCommunication = commonRemoteCommunication;
	}

	@Override
	public void startServer() {
		this.commonRemoteCommunication.startServer();
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
		return this.commonRemoteCommunication.activate();
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
