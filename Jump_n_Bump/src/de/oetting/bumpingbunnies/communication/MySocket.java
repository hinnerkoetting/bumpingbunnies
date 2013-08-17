package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

public interface MySocket {

	void close() throws IOException;

	void connect() throws IOException;

	void sendMessage(String message);

	String blockingReceive();

}
