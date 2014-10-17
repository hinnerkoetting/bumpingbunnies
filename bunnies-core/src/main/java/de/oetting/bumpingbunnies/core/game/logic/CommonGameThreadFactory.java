package de.oetting.bumpingbunnies.core.game.logic;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

/**
 * Unified creation of gamethread belongs here.
 *
 */
public class CommonGameThreadFactory {

	private static final int MAX_GAME_LOOPS_PER_SECOND = 100;

	public static GameThread create(GameStepController worldController, ThreadErrorCallback errorCallback) {
		ThreadLoop threadLoop = new ThreadLoop(new GameThreadStep(worldController), MAX_GAME_LOOPS_PER_SECOND);
		return new GameThread(worldController, threadLoop, errorCallback);
	}
}
