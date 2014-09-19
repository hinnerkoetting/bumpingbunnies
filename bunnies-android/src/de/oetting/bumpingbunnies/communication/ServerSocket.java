package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public interface ServerSocket {

	MySocket accept() throws IOException;

	void close() throws IOException;
}
