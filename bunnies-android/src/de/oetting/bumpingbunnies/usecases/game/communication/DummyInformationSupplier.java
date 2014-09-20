package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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

	@Override
	public boolean belongsToPlayer(Player p) {
		return false;
	}

}
