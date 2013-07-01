package de.oetting.bumpingbunnies.usecases.game.communication;

public interface InformationSupplier {

	void cancel();

	void start();

	NetworkToGameDispatcher getGameDispatcher();

}