package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidObjectsDrawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;

/**
 * All game logic and drawing of the game is executed in this thread.<br>
 * During each loop it will: <li>
 * It will call the {@link GameStepController} for gamelogic.</li> <br>
 * <li>The {@link AndroidObjectsDrawer} is called to draw</li><br>
 * <li>It will handle fps</li
 * 
 * 
 */
public class GameThread extends Thread implements GameScreenSizeChangeListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);

	private final GameStepController worldController;
	private final GameThreadState state;

	private boolean running;
	private boolean canceled;

	private final Drawer drawer;
	private int fpsLimitation;

	public GameThread(Drawer drawer, GameStepController worldController, GameThreadState gameThreadState) {
		super("Main Game Thread");
		this.drawer = drawer;
		this.worldController = worldController;
		this.running = true;
		this.state = gameThreadState;
		this.fpsLimitation = 29;
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
		this.state.setLastRun(System.currentTimeMillis());
		while (!this.canceled) {
			if (this.running) {
				if (!shouldStepGetSkipped()) {
					nextWorldStep();
					drawGame();
					this.state.increaseFps();
				}
			} else {
				sleep(100);
			}
		}
	}

	private boolean shouldStepGetSkipped() {
		long millisecondsSinceLastRun = System.currentTimeMillis() - this.state.getLastRun();
		int milliPerSecond = 1000;
		return millisecondsSinceLastRun < milliPerSecond / this.fpsLimitation;
	}

	private void nextWorldStep() throws InterruptedException {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - this.state.getLastRun();
		if (delta > 10) {
			this.state.setLastRun(currentTime);
			if (delta > 1000) {
				LOGGER.warn("skipping frames");
				return;
			}
			LOGGER.debug("Delta %d", delta);
			if (isLastResetOneSecondAgo(currentTime)) {
				this.state.resetFps(currentTime);
			}
			this.worldController.nextStep(delta);
		} else {
			sleep(10 - delta);
		}
	}

	private boolean isLastResetOneSecondAgo(long currentTime) {
		return currentTime - this.state.getLastFpsReset() > 1000;
	}

	private void drawGame() {

	}

	public void setRunning(boolean b) {
		this.running = b;
		LOGGER.info("Running " + b);
	}

	public void cancel() {
		this.canceled = true;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.drawer.setNeedsUpdate(true);
	}

	public void addAllJoinListeners(GameMain gameMain) {
		this.worldController.addAllJoinListeners(gameMain);
		gameMain.addJoinListener(this.drawer);
	}

	/**
	 * Coupling to android
	 */
	@Deprecated
	public Drawer getDrawer() {
		return drawer;
	}

}
