package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSender {

	void sendPlayerCoordinates();

	boolean sendsStateToPlayer(Player p);

}