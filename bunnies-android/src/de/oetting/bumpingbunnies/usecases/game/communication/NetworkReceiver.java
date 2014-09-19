package de.oetting.bumpingbunnies.usecases.game.communication;

public interface NetworkReceiver {

	void cancel();

	void start();

	NetworkToGameDispatcher getGameDispatcher();

}