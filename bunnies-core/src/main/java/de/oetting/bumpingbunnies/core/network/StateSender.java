package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface StateSender {

	void sendPlayerCoordinates();

	boolean sendsStateToPlayer(Bunny p);

}