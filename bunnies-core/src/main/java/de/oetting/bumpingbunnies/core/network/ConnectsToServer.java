package de.oetting.bumpingbunnies.core.network;


public interface ConnectsToServer {

	void connectionNotSuccesful(String message);

	void connectToServerSuccesfull(MySocket mmSocket);
}
