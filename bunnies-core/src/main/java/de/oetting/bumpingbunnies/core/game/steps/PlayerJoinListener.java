package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface PlayerJoinListener {

	void newPlayerJoined(Player p);

	void playerLeftTheGame(Player p);
}
