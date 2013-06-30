package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public interface ClientConnectedSuccesfullCallback {

	void clientConnectedSucessfull(final MySocket socket);
}
