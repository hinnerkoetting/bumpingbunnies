package de.jumpnbump.usecases.networkRoom;

import de.jumpnbump.usecases.start.communication.MySocket;

public interface ConnectionToServerSuccesfullCallback {

	void connectToServerSuccesfull(MySocket socket);
}
