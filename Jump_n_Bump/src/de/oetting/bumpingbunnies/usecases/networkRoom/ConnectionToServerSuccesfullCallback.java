package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public interface ConnectionToServerSuccesfullCallback {

	void connectToServerSuccesfull(MySocket socket);
}
