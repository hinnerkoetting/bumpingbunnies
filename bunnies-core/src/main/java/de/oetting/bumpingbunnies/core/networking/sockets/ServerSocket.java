package de.oetting.bumpingbunnies.core.networking.sockets;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.network.MySocket;

/**
 * A Serverside sockets which can accept requests from new clients.
 */
public interface ServerSocket {

	MySocket accept() throws IOException;

	void close() throws IOException;
}
