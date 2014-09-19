package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public interface ConnectsToServer {

	void connectionNotSuccesful(String message);

	void connectToServerSuccesfull(MySocket mmSocket);
}
