package de.jumpnbump.usecases.game.communication;

public interface InformationSupplier {

	void addObserver(int id, NetworkListener listener);

	void cancel();

}