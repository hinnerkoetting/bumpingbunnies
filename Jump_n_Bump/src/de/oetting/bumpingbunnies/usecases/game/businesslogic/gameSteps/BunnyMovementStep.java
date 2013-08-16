package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementCalculation;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction {

	private final List<PlayerMovementCalculation> playermovements;
	private final BunnyKillChecker killChecker;

	public BunnyMovementStep(List<PlayerMovementCalculation> playermovements,
			BunnyKillChecker killChecker) {
		super();
		this.killChecker = killChecker;
		this.playermovements = Collections.unmodifiableList(playermovements);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementCalculation movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
			checkForJumpedPlayers();
		}
		this.killChecker.checkForPlayerOutsideOfGameZone();
	}

	private void checkForJumpedPlayers() {
		this.killChecker.checkForJumpedPlayers();
	}

}
