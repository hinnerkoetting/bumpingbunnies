package de.oetting.bumpingbunnies.core.game.steps;

/**
 * Runs during the main game.
 * 
 */
public interface GameStepAction {

	void executeNextStep(long deltaStepsSinceLastCall);
}
