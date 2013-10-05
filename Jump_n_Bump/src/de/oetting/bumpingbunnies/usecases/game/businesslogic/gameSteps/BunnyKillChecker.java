package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;

public interface BunnyKillChecker extends PlayerJoinListener {

	void checkForJumpedPlayers();

	void checkForPlayerOutsideOfGameZone();
}
