package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class DummyRemoteSender implements RemoteSender {

	@Override
	public void sendPlayerCoordinates(Player player) {

	}

	@Override
	public void cancel() {

	}

	@Override
	public void sendPlayerCoordinates(PlayerState playerState) {
	}

}
