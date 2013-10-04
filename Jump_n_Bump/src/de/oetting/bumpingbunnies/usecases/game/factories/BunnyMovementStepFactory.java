package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;

public class BunnyMovementStepFactory {

	public static BunnyMovementStep create(BunnyKillChecker killChecker,
			PlayerMovementCalculationFactory factory) {
		BunnyMovementStep step = new BunnyMovementStep(killChecker, factory);
		return step;
	}
}
