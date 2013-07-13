package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Host logic what should be done when a bunny jumps on another bunny.
 * 
 */
public class HostBunnyKillChecker implements BunnyKillChecker {
	private static final int KILL_TIME_MILLISECONDS = 1000;
	private static final Logger LOGGER = LoggerFactory.getLogger(HostBunnyKillChecker.class);
	private final CollisionDetection collisionDetection;
	private final List<Player> allPlayers;
	private final SpawnPointGenerator spawnPointGenerator;

	public HostBunnyKillChecker(CollisionDetection collisionDetection, List<Player> allPlayers, SpawnPointGenerator spawnPointGenerator) {
		super();
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
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

	private void handleJumpedPlayer(Player playerUnder, Player playerTop) {
		increaseScore(playerTop);
		killPlayer(playerUnder);
		resetCoordinate(playerUnder);
		revivePlayerDelayed(playerUnder);
	}

	private void resetCoordinate(Player playerUnder) {
		ResetToScorePoint.resetPlayerToSpawnPoint(this.spawnPointGenerator, playerUnder);
	}

	private void killPlayer(Player player) {
		player.setDead(true);
	}

	private void increaseScore(Player playerTop) {
		playerTop.increaseScore(1);
	}

	private void revivePlayerDelayed(final Player player) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(KILL_TIME_MILLISECONDS);
				} catch (InterruptedException e) {
					LOGGER.error("exception", e);
				}
				player.setDead(false);
			}
		}).start();
	}
}
