package de.jumpnbump.usecases.networkRoom;

import de.jumpnbump.usecases.start.communication.MySocket;

public interface ClientConnectedSuccesfullCallback {

	void clientConnectedSucessfull(final MySocket socket);
}
