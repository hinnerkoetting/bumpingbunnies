package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.StateSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
