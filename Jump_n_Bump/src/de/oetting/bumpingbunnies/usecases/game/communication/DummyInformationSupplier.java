package de.oetting.bumpingbunnies.usecases.game.communication;

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
