package de.jumpnbump.usecases.game.communication;

import de.jumpnbump.usecases.game.model.Player;

public interface RemoteSender {

	public void sendPlayerCoordinates(Player player);

	public void cancel();
}