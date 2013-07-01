package de.oetting.bumpingbunnies.usecases.game.communication;

public interface StateSender {

	void sendPlayerCoordinates();

	RemoteSender getRemoteSender();

}