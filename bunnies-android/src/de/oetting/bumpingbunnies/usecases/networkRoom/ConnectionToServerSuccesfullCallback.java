package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public interface ConnectionToServerSuccesfullCallback {

	void connectToServerSuccesfull(MySocket socket);
}
