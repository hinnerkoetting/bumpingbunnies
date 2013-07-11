package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;

/**
 * Calls all components which should be executed during the regular game loop.
 * 
 */
public class GameStepController {

	private final UserInputStep userInputStep;
	private final BunnyMovementStep movements;
	private final SendingCoordinatesStep sendingCoordinates;

	public GameStepController(
			UserInputStep userInputStep, BunnyMovementStep movements,
			SendingCoordinatesStep sendingCoordinates) {
		this.userInputStep = userInputStep;
		this.movements = movements;
		this.sendingCoordinates = sendingCoordinates;
	}

	public void nextStep(long delta) {
		this.userInputStep.executeNextStep(delta);
		this.movements.executeNextStep(delta);
		this.sendingCoordinates.executeNextStep(delta);

	}

}
