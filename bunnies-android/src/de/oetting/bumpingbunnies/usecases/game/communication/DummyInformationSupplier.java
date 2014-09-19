package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;

public class DummyInformationSupplier implements NetworkReceiver {

	@Override
	public void cancel() {
	}

	@Override
	public void start() {
	}

	@Override
	public NetworkToGameDispatcher getGameDispatcher() {
		throw new IllegalArgumentException("Should not happen");
	}

}
