package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface PlayerJoinListener {

	void newPlayerJoined(Player p);

	void playerLeftTheGame(Player p);
}
