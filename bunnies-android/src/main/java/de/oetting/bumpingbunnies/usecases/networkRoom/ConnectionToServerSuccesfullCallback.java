package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.core.network.MySocket;

public interface ConnectionToServerSuccesfullCallback {

	void connectToServerSuccesfull(MySocket socket);
}
