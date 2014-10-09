package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public interface PlayerDisconnectedCallback {

	void playerDisconnected(Opponent opponent);
}
