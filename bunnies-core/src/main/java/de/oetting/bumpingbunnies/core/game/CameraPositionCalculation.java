package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.game.steps.GameStepAction;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class CameraPositionCalculation implements GameStepAction {

	protected static final int SLOW_SCROLLING_SPEED = ModelConstants.STANDARD_WORLD_SIZE / 20000;
	protected static final int MEDIUM_SCROLLING_SPEED = ModelConstants.STANDARD_WORLD_SIZE / 5000;
	protected static final int FAST_SCROLLING_SPEED = ModelConstants.STANDARD_WORLD_SIZE / 1000;
	private final Bunny movedPlayer;

	private long currentScreenX;
	private long currentScreenY;
	private int zoom;

	public CameraPositionCalculation(Bunny movedPlayer, int zoom) {
		this.movedPlayer = movedPlayer;
		this.zoom = zoom;
		this.currentScreenX = 0;
		this.currentScreenY = 0;
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		updateScreenPosition(deltaStepsSinceLastCall);
	}

	void updateScreenPosition(long deltaStepsSinceLastCall) {
		smoothlyUpdateScreenPosition(deltaStepsSinceLastCall);
	}

	public void immediateUpdateScreenPosition() {
		this.currentScreenX = this.movedPlayer.getCenterX();
		this.currentScreenY = this.movedPlayer.getCenterY();
	}

	/**
	 * we want to smoothly move to the players position if he is dead. This will
	 * avoid fast jumps because of next spawnpoint.
	 */
	public void smoothlyUpdateScreenPosition(long delta) {
		int diffBetweenPlayerAndScreenX = (int) (this.movedPlayer.getCenterX() - this.currentScreenX);
		int diffBetweenPlayerAndScreenY = (int) (this.movedPlayer.getCenterY() - this.currentScreenY);
		int scrollingSpeed = determineScrollingSpeed(diffBetweenPlayerAndScreenX, diffBetweenPlayerAndScreenY);
		int maxScrollValueX = (int) (scrollingSpeed * delta * Math.signum(diffBetweenPlayerAndScreenX));
		int maxScrollValueY = (int) (scrollingSpeed * delta * Math.signum(diffBetweenPlayerAndScreenY));
		if (Math.abs(diffBetweenPlayerAndScreenX) <= Math.abs(maxScrollValueX)) {
			this.currentScreenX = this.movedPlayer.getCenterX();
		} else {
			this.currentScreenX = this.currentScreenX + maxScrollValueX;
		}
		if (Math.abs(diffBetweenPlayerAndScreenY) <= Math.abs(maxScrollValueY)) {
			this.currentScreenY = this.movedPlayer.getCenterY();
		} else {
			this.currentScreenY = this.currentScreenY + maxScrollValueY;
		}
	}

	private int determineScrollingSpeed(int diffBetweenPlayerAndScreenX, int diffBetweenPlayerAndScreenY) {
		int max = Math.max(Math.abs(diffBetweenPlayerAndScreenX), Math.abs(diffBetweenPlayerAndScreenY));
		if (max < ModelConstants.STANDARD_WORLD_SIZE / 100 / zoom)
			return SLOW_SCROLLING_SPEED;
		if (max < ModelConstants.STANDARD_WORLD_SIZE / 25 / zoom)
			return MEDIUM_SCROLLING_SPEED;
		return FAST_SCROLLING_SPEED;
	}
 
	public long getCurrentScreenX() {
		return this.currentScreenX;
	}

	public long getCurrentScreenY() {
		return this.currentScreenY;
	}

	public void setCurrentScreenX(int currentScreenX) {
		this.currentScreenX = currentScreenX;
	}

	public void setCurrentScreenY(int currentScreenY) {
		this.currentScreenY = currentScreenY;
	}

}
