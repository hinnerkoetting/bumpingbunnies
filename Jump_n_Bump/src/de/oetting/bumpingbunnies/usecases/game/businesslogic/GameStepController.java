package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.PlayerReviver;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;

/**
 * Calls all components which should be executed during the regular game loop.
 * 
 */
public class GameStepController {

	private static final int MILLISECONDS_PER_STEP = 5;
	private final UserInputStep userInputStep;
	private final BunnyMovementStep movements;
	private final SendingCoordinatesStep sendingCoordinates;
	private final PlayerReviver reviver;
	// because we execute one step per multiple milliseconds
	// it may happen that some milliseconds can not be processed
	// this remaining time is stored in this variable
	// and used for the next cycle
	private long remainingDeltaFromLastRun = 0;
	private final CameraPositionCalculation cameraPositionCalculator;

	public GameStepController(
			UserInputStep userInputStep, BunnyMovementStep movements,
			SendingCoordinatesStep sendingCoordinates, PlayerReviver reviver, CameraPositionCalculation cameraPositionCalculator) {
		this.userInputStep = userInputStep;
		this.movements = movements;
		this.sendingCoordinates = sendingCoordinates;
		this.reviver = reviver;
		this.cameraPositionCalculator = cameraPositionCalculator;
	}

	public void nextStep(long delta) {
		long deltaWithOldRemainingTime = delta + this.remainingDeltaFromLastRun;
		long numberSteps = deltaWithOldRemainingTime / MILLISECONDS_PER_STEP;
		this.remainingDeltaFromLastRun = deltaWithOldRemainingTime % MILLISECONDS_PER_STEP;
		this.userInputStep.executeNextStep(numberSteps);
		this.movements.executeNextStep(numberSteps);
		this.sendingCoordinates.executeNextStep(numberSteps);
		this.reviver.executeNextStep(numberSteps);
		this.cameraPositionCalculator.executeNextStep(delta);
	}

	public void addJoinListener(GameMain gameMain) {
		gameMain.addJoinListener(this.movements);
	}
}
