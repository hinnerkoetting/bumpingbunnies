package de.oetting.bumpingbunnies.core.networking;


public interface ConnectsToServer {

	void connectionNotSuccesful(String message);

	void connectToServerSuccesfull(MySocket mmSocket);
}
