package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface RemoteSender {

	public void sendPlayerCoordinates(Player player);

	public void cancel();
}