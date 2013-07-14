package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction {

	private final List<PlayerMovementController> playermovements;
	private final BunnyKillChecker killChecker;

	public BunnyMovementStep(List<PlayerMovementController> playermovements,
			BunnyKillChecker killChecker) {
		super();
		this.killChecker = killChecker;
		this.playermovements = Collections.unmodifiableList(playermovements);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementController movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
			checkForJumpedPlayers();
		}
		this.killChecker.checkForPlayerOutsideOfGameZone();
	}

	private void checkForJumpedPlayers() {
		this.killChecker.checkForJumpedPlayers();
	}

}
