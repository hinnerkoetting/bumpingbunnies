package de.oetting.bumpingbunnies.usecases.game.communication;

public class DummyStateSender implements StateSender {

	@Override
	public void sendPlayerCoordinates() {
	}

	@Override
	public RemoteSender getRemoteSender() {
		return new DummyRemoteSender();
	}

}
