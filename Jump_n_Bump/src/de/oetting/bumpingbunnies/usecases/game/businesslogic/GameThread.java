package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.List;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameThread extends Thread implements SurfaceHolder.Callback,
		GameScreenSizeChangeListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GameThread.class);
	private final Drawer drawer;
	private final WorldController worldController;
	private SurfaceHolder holder;
	private GameThreadState state;

	private boolean running;
	private boolean isDrawingPossible;
	private boolean canceled;

	public GameThread(Drawer drawer, WorldController worldController,
			GameThreadState gameThreadState) {
		super("Main Game Thread");
		this.drawer = drawer;
		this.worldController = worldController;
		this.running = true;
		this.isDrawingPossible = false;
		this.state = gameThreadState;
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
				nextWorldStep();
				drawGame();
				this.state.increaseFps();
			} else {
				sleep(100);
			}
		}
	}

	private void nextWorldStep() throws InterruptedException {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - this.state.getLastRun();
		if (delta > 10) {
			this.state.setLastRun(currentTime);
			if (delta > 1000) {
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
		return System.currentTimeMillis() - this.state.getLastFpsReset() > 1000;

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

	public void switchInputServices(List<InputService> createInputServices) {
		this.worldController.switchInputServices(createInputServices);

	}

	@Override
	public void setNewSize(int width, int height) {
		this.drawer.setNeedsUpdate(true);
	}

	public Object getCurrentState() {
		return this.worldController.getAllPlayers();

	}

	public void applyState(Object state) {
		this.worldController.applyPlayers((List<Player>) state);

	}
}
