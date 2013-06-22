package de.jumpnbump.usecases.start.communication;

import java.io.IOException;

public interface ServerSocket {

	MySocket accept() throws IOException;

	void close() throws IOException;
}
