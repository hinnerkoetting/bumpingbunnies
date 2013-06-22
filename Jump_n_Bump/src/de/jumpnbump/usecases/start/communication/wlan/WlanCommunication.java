package de.jumpnbump.usecases.start.communication.wlan;

import de.jumpnbump.usecases.start.communication.RemoteCommunication;
import de.jumpnbump.usecases.start.communication.RemoteCommunicationImpl;
import de.jumpnbump.usecases.start.communication.ServerDevice;

public class WlanCommunication implements RemoteCommunication {
	private RemoteCommunicationImpl commonRemoteCommunication;

	public WlanCommunication(RemoteCommunicationImpl commonRemoteCommunication) {
		super();
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
	public void findServer() {
		this.commonRemoteCommunication.findServer();
	}

}
