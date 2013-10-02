package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;

/**
 * All game logic and drawing of the game is executed in this thread.<br>
 * During each loop it will: <li>
 * It will call the {@link GameStepController} for gamelogic.</li> <br>
 * <li>The {@link Drawer} is called to draw</li><br>
 * <li>It will handle fps</li
 * 
 * 
 */
public class GameThread extends Thread implements SurfaceHolder.Callback,
		GameScreenSizeChangeListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GameThread.class);
	private final Drawer drawer;
	private final GameStepController worldController;
	private final GameThreadState state;
	private final CameraPositionCalculation camCalculator;
	private SurfaceHolder holder;

	private boolean running;
	private boolean isDrawingPossible;
	private boolean canceled;
	private final boolean altPixelMode;
	private int fpsLimitation;

	public GameThread(Drawer drawer, GameStepController worldController,
			GameThreadState gameThreadState, boolean altPixelMode, CameraPositionCalculation camCalculator) {
		super("Main Game Thread");
		this.drawer = drawer;
		this.worldController = worldController;
		this.altPixelMode = altPixelMode;
		this.camCalculator = camCalculator;
		this.running = true;
		this.isDrawingPossible = false;
		this.state = gameThreadState;
		this.fpsLimitation = 30;
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
			if (this.running && this.isDrawingPossible) {
				if (!shouldStepGetSkipped()) {
					this.camCalculator.updateScreenPosition();
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
		Canvas lockCanvas = this.holder.lockCanvas();
		try {
			if (lockCanvas != null) {
				synchronized (this.holder) {
					this.drawer.draw(lockCanvas);
				}
			}
		} finally {
			if (lockCanvas != null) {
				this.holder.unlockCanvasAndPost(lockCanvas);
			}
		}
	}

	public void setRunning(boolean b) {
		this.running = b;
		LOGGER.info("Running " + b);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		if (this.altPixelMode) {
			holder.setFormat(PixelFormat.OPAQUE);
		} else {
			holder.setFormat(PixelFormat.RGBA_8888);
		}
		this.isDrawingPossible = true;
		LOGGER.info("Surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.isDrawingPossible = false;
	}

	public void cancel() {
		this.canceled = true;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.drawer.setNeedsUpdate(true);
	}
}
