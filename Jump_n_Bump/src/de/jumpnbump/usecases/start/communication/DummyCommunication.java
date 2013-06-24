package de.jumpnbump.usecases.start.communication;

public class DummyCommunication implements RemoteCommunication {

	@Override
	public void startServer() {
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
	public void findServer(String address) {
	}

}
