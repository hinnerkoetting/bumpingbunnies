package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ThreadLoop {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLoop.class);
	private final GameThreadState state;
	private final OneLoopStep runner;
	private final double minTimeBetweenSteps;

	public ThreadLoop(OneLoopStep runner, int fpsLimitation) {
		this(runner, fpsLimitation, new GameThreadState());
	}

	public ThreadLoop(OneLoopStep runner, int fpsLimitation, GameThreadState state) {
		this.state = state;
		this.runner = runner;
		minTimeBetweenSteps = 1000.0 / fpsLimitation;
		state.setLastRun(System.currentTimeMillis());
	}

	public void nextStep() {
		if (!shouldStepGetSkipped()) {
			nextWorldStep();
		} else {
			sleepUntilNextStep();
		}
	}

	private void sleepUntilNextStep() {
		try {
			long millisecondsToNextStep = computeMillisecondsToNextStep();
			Thread.sleep(millisecondsToNextStep > 0 ? millisecondsToNextStep : 0);
		} catch (InterruptedException e) {
		}
	}

	private boolean shouldStepGetSkipped() {
		return computeMillisecondsToNextStep() > 0;
	}

	private long computeMillisecondsToNextStep() {
		long millisecondsSinceLastRun = System.currentTimeMillis() - this.state.getLastRun();
		return (long) (minTimeBetweenSteps - millisecondsSinceLastRun);
	}

	private void nextWorldStep() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - this.state.getLastRun();
		this.state.setLastRun(currentTime);
		if (delta > 1000) {
			LOGGER.warn("skipping frames");
			return;
		}
		LOGGER.verbose("Delta %d", delta);
		if (isLastResetOneSecondAgo(currentTime)) {
			this.state.resetFps(currentTime);
		}
		this.runner.nextStep(delta);
		this.state.increaseFps();
	}

	private boolean isLastResetOneSecondAgo(long currentTime) {
		return currentTime - this.state.getLastFpsReset() > 1000;
	}

}
