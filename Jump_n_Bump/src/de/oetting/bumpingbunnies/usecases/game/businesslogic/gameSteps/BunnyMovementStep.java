package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;

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
	}

	private void checkForJumpedPlayers() {
		this.killChecker.checkForJumpedPlayers();
	}

}
