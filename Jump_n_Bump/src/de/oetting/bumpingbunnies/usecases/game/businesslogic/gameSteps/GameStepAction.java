package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

/**
 * Runs during the main game.
 * 
 */
public interface GameStepAction {

	void executeNextStep(long deltaStepsSinceLastCall);
}
