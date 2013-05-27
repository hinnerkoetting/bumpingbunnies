package de.jumpnbump.usecases.game.communication;

import de.jumpnbump.usecases.game.model.Player;

public interface StateSender {

	void sendPlayerCoordinates(Player player);

	void cancel();
}