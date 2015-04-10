package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class DummyStateSender implements StateSender {

	private final Bunny recipient;

	public DummyStateSender(Bunny player) {
		super();
		this.recipient = player;
	}

	@Override
	public void sendPlayerCoordinates() {
	}

	@Override
	public boolean sendsStateToPlayer(Bunny p) {
		return this.recipient.equals(p);
	}

}
