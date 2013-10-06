package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.communication.MySocket;

public interface AcceptsClientConnections {

	void clientConnectedSucessfull(final MySocket socket);

}
