package de.oetting.bumpingbunnies.core.game.steps;


public interface BunnyKillChecker extends PlayerJoinListener {

	void checkForJumpedPlayers();

	void checkForPlayerOutsideOfGameZone();

}
