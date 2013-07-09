package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.communication.MySocket;

public interface ConnectionToServerSuccesfullCallback {

	void connectToServerSuccesfull(MySocket socket);
}
