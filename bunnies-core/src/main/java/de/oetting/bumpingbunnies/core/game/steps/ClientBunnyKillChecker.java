package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

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
	public void newEvent(Bunny p) {
	}

	@Override
	public void removeEvent(Bunny p) {
	}

	@Override
	public void addJoinListener(JoinObserver main) {
	}
}
