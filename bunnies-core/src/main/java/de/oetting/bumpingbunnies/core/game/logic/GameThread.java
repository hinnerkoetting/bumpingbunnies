package de.oetting.bumpingbunnies.core.game.logic;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.JoinObserver;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

/**
 * Game logic is executed in this thread.<br>
 * Executions per second are limited.
 * 
 */
public class GameThread extends BunniesThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);

	private final GameStepController worldController;
	private final ThreadLoop loop;

	private boolean running;
	private boolean canceled;

	public GameThread(GameStepController worldController, ThreadLoop loop, ThreadErrorCallback errorCallback) {
		super("Main Game Thread", errorCallback);
		this.worldController = worldController;
		this.running = true;
		this.loop = loop;
	}

	protected void doRun() throws InterruptedException {
		while (!this.canceled) {
			if (this.running) {
				loop.nextStep();
			} else {
				sleep(100);
			}
		}
	}

	public void setRunning(boolean b) {
		this.running = b;
		LOGGER.info("Running " + b);
	}

	public void cancel() {
		this.canceled = true;
	}

	public void addAllJoinListeners(JoinObserver gameMain) {
		this.worldController.addAllJoinListeners(gameMain);

	}

	public void pause(boolean pause) {
		worldController.setPause(pause);
	}

	public boolean isPaused() {
		return worldController.isPaused();
	}

}
