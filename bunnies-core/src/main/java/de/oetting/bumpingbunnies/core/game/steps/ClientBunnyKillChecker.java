package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public class ClientBunnyKillChecker implements BunnyKillChecker {

	public ClientBunnyKillChecker() {
		super();
	}

	@Override
	public void checkForJumpedPlayers() {
		// client should not check because otherwise he might think that he
		// killed another players
		// even if this is not registered on the server
		// for (Player player : this.allPlayers) {
	}

	@Override
	public void checkForPlayerOutsideOfGameZone() {
	}

	@Override
	public void newPlayerJoined(Player p) {
	}

	@Override
	public void playerLeftTheGame(Player p) {
	}
}
