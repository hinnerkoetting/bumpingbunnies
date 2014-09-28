package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.JoinObserver;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

/**
 * All game logic and drawing of the game is executed in this thread.<br>
 * During each loop it will: <li>
 * It will call the {@link GameStepController} for gamelogic.</li> <br>
 * <li>The {@link AndroidObjectsDrawer} is called to draw</li><br>
 * <li>It will handle fps</li
 * 
 * 
 */
public class GameThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);

	private final GameStepController worldController;
	private final ThreadLoop loop;

	private boolean running;
	private boolean canceled;

	public GameThread(GameStepController worldController) {
		super("Main Game Thread");
		setDaemon(true);
		this.worldController = worldController;
		this.running = true;
		loop = new ThreadLoop(new OneLoopStep() {

			@Override
			public void nextStep(long delta) {
				GameThread.this.worldController.nextStep(delta);
			}
		}, 100);
	}

	@Override
	public void run() {
		try {
			internalRun();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void internalRun() throws InterruptedException {
		LOGGER.info("start game thread");
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

}
