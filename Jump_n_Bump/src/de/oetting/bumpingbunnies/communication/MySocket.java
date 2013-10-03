package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public interface MySocket {

	void close() throws IOException;

	void connect() throws IOException;

	void sendMessage(String message);

	String blockingReceive();

	/**
	 * Fast connection is udp for example. bluetooth would return itself, also udp does return itself
	 */
	MySocket createFastConnection();

	Opponent getOwner();
}
