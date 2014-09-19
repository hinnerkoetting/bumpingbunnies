package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSender {

	void sendPlayerCoordinates();

	boolean sendsStateToPlayer(Player p);

}