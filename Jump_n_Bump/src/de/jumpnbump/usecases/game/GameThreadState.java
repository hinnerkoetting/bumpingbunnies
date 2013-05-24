package de.jumpnbump.usecases.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameThreadState {

	private long lastRun;
	private int fpsCounter;
	private int lastFpsCount;
	private long lastFpsReset;
	private Paint paint;

	public GameThreadState() {
		this.paint = new Paint();
		this.paint.setColor(Color.BLACK);
		this.paint.setTextSize(30);
	}

	public long getLastRun() {
		return this.lastRun;
	}

	public void setLastRun(long lastRun) {
		this.lastRun = lastRun;
	}

	public int getFpsCounter() {
		return this.fpsCounter;
	}

	public void setFpsCounter(int fpsCounter) {
		this.fpsCounter = fpsCounter;
	}

	public long getLastFpsReset() {
		return this.lastFpsReset;
	}

	public void resetFps(long currentTime) {
		this.lastFpsReset = currentTime;
		this.lastFpsCount = this.fpsCounter;
		this.fpsCounter = 0;
	}

	public void increaseFps() {
		this.fpsCounter++;
	}

	public void drawFps(Canvas canvas) {
		canvas.drawText(String.format("FPS: %d", this.lastFpsCount), 100, 100,
				this.paint);
	}
}
