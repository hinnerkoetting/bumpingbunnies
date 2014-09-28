package de.oetting.bumpingbunnies.communication.bluetooth;

import de.oetting.bumpingbunnies.communication.ServerDevice;
import de.oetting.bumpingbunnies.communication.SocketFactory;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.sockets.ServerSocket;

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
