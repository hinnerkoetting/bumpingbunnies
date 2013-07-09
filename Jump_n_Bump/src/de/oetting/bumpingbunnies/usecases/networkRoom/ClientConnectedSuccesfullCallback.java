package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.communication.MySocket;

public interface ClientConnectedSuccesfullCallback {

	void clientConnectedSucessfull(final MySocket socket);
}
