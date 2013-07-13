package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class ClientBunnyKillChecker implements BunnyKillChecker {

	private final CollisionDetection collisionDetection;
	private final List<Player> allPlayers;

	public ClientBunnyKillChecker(CollisionDetection collisionDetection, List<Player> allPlayers) {
		super();
		this.collisionDetection = collisionDetection;
		this.allPlayers = Collections.unmodifiableList(allPlayers);
	}

	@Override
	public void checkForJumpedPlayers() {
		for (Player player : this.allPlayers) {
			Player playerUnder = this.collisionDetection.findPlayerThisPlayerIsStandingOn(player);
			if (playerUnder != null) {
				handleJumpedPlayer(playerUnder, player);
			}
		}
	}

	/**
	 * Simulate Server behaviour
	 */
	private void handleJumpedPlayer(Player playerUnder, Player player) {
		playerUnder.setDead(true);
		new BunnyDelayedReviver(playerUnder, BunnyDelayedReviver.KILL_TIME_CLIENT_MILLISECONDS).start();
	}
}
