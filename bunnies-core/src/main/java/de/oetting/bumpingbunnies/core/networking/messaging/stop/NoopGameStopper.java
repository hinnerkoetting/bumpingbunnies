package de.oetting.bumpingbunnies.core.networking.messaging.stop;

public class NoopGameStopper implements GameStopper {

	@Override
	public void stopGame() {
	}

	@Override
	public void onDisconnect() {
	}

}
