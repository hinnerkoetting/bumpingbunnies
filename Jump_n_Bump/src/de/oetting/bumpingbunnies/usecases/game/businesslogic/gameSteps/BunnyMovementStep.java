package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction {

	private final List<PlayerMovementController> playermovements;
	private final SpawnPointGenerator spawnPointGenerator;
	private final BunnyKillChecker killChecker;

	public BunnyMovementStep(List<PlayerMovementController> playermovements, SpawnPointGenerator spawnPointGenerator,
			BunnyKillChecker killChecker) {
		super();
		this.spawnPointGenerator = spawnPointGenerator;
		this.killChecker = killChecker;
		this.playermovements = Collections.unmodifiableList(playermovements);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementController movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
			checkForJumpedPlayers();
		}
		killPlayersOutOfPlayZone();
	}

	private void checkForJumpedPlayers() {
		this.killChecker.checkForJumpedPlayers();
	}

	private void killPlayersOutOfPlayZone() {
		for (PlayerMovementController movement : this.playermovements) {
			Player player = movement.getPlayer();
			if (player.getCenterY() < -ModelConstants.MAX_VALUE * 0.1) {
				player.increaseScore(-1);
				resetCoordinate(player);
			}
		}

	}

	private void resetCoordinate(Player playerUnder) {
		ResetToScorePoint.resetPlayerToSpawnPoint(this.spawnPointGenerator, playerUnder);
	}
}
