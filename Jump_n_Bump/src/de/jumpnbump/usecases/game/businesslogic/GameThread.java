package de.jumpnbump.usecases.game.businesslogic;

import java.util.List;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.GameThreadState;

public class GameThread extends Thread implements SurfaceHolder.Callback {

	private static final MyLog LOGGER = Logger.getLogger(GameThread.class);
	private final Drawer drawer;
	private SurfaceHolder holder;
	private GameThreadState state;

	private boolean running;
	private boolean isDrawingPossible;
	private final WorldController worldController;
	private boolean canceled;

	public GameThread(Drawer drawer, WorldController worldController,
			GameThreadState gameThreadState) {
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
			sleep(1);
		}
		this.state.increaseFps();
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
		this.worldController.destroy();
	}

	public void switchInputServices(List<InputService> createInputServices) {
		this.worldController.switchInputServices(createInputServices);

	}
}
