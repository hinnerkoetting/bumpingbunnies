package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public interface ServerDevice {
	MySocket createClientSocket();
}
