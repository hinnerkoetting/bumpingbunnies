package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface StateSender {

	void sendPlayerCoordinates();

	boolean sendsStateToPlayer(Player p);

}