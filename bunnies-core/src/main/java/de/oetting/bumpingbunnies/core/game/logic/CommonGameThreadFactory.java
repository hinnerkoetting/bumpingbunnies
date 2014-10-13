package de.oetting.bumpingbunnies.core.game.logic;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

/**
 * Unified creation of gamethread belongs here.
 *
 */
public class CommonGameThreadFactory {

	public static GameThread create(GameStepController worldController, ThreadErrorCallback errorCallback) {
		ThreadLoop threadLoop = new ThreadLoop(new GameThreadStep(worldController), 100);
		return new GameThread(worldController, threadLoop, errorCallback);
	}
}
