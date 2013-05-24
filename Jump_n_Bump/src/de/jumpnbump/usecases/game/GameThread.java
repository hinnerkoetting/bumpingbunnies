package de.jumpnbump.usecases.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.graphics.Drawer;

public class GameThread extends Thread implements SurfaceHolder.Callback {

	private static final MyLog LOGGER = Logger.getLogger(GameThread.class);
	private final Drawer drawer;
	private SurfaceHolder holder;

	private boolean running;
	private boolean isDrawingPossible;
	private final WorldController worldController;

	public GameThread(Drawer drawer, WorldController worldController) {
		this.drawer = drawer;
		this.worldController = worldController;
		this.running = true;
		this.isDrawingPossible = false;
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
		while (true) {
			if (this.running && this.isDrawingPossible) {
				nextWorldStep();
				drawGame();
				sleep(1);
			} else {
				sleep(100);
			}
		}
	}

	private void nextWorldStep() {
		this.worldController.nextStep();
	}

	private void drawGame() {
		Canvas lockCanvas = this.holder.lockCanvas();
		try {
			synchronized (this.holder) {
				this.drawer.draw(lockCanvas);
			}
		} finally {
			this.holder.unlockCanvasAndPost(lockCanvas);
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

}
