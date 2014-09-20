package de.oetting.bumpingbunnies.core.game.main;

public class GameThreadState {

	private long lastRun;
	private int fpsCounter;
	private int lastFpsCount;
	private long lastFpsReset;

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

	public int getLastFpsCount() {
		return this.lastFpsCount;
	}
}
