package de.oetting.bumpingbunnies.communication.bluetooth;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.communication.ServerSocket;
import de.oetting.bumpingbunnies.communication.SocketFactory;

public class DummySocketFactory implements SocketFactory {

	@Override
	public ServerSocket create() {
		throw new IllegalArgumentException("not implemented");
	}

	@Override
	public MySocket createClientSocket(ServerDevice serverDevice) {
		throw new IllegalArgumentException("not implemented");
	}

}