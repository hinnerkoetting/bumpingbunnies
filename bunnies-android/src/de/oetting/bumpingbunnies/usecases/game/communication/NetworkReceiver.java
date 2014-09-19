package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;

public interface NetworkReceiver {

	void cancel();

	void start();

	NetworkToGameDispatcher getGameDispatcher();

}