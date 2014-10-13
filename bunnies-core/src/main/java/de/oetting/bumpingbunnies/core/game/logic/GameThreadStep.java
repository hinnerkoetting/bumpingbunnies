package de.oetting.bumpingbunnies.core.game.logic;

import de.oetting.bumpingbunnies.core.game.main.OneLoopStep;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;

public class GameThreadStep implements OneLoopStep {

	private final GameStepController worldController;

	public GameThreadStep(GameStepController worldController) {
		this.worldController = worldController;
	}

	@Override
	public void nextStep(long delta) {
		worldController.nextStep(delta);
	}

}
