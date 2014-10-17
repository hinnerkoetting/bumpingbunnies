package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public interface PlayerDisconnectedCallback {

	void playerDisconnected(ConnectionIdentifier opponent);

	void playerDisconnected(int playerId);

}
