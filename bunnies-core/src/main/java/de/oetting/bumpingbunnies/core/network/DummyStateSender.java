package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public class DummyStateSender implements StateSender {

	private final Player recipient;

	public DummyStateSender(Player player) {
		super();
		this.recipient = player;
	}

	@Override
	public void sendPlayerCoordinates() {
	}

	@Override
	public boolean sendsStateToPlayer(Player p) {
		return this.recipient.equals(p);
	}

}
