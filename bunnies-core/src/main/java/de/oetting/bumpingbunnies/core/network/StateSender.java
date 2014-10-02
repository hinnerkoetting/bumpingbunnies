package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSender {

	void sendPlayerCoordinates();

	boolean sendsStateToPlayer(Player p);

}