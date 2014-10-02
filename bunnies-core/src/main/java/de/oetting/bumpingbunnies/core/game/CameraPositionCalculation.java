package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.game.steps.GameStepAction;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class CameraPositionCalculation implements GameStepAction {

	protected static final int SCROLLING_WHILE_PLAYER_IS_DEAD = ModelConstants.STANDARD_WORLD_SIZE / 200;
	private final Player movedPlayer;

	private long currentScreenX;
	private long currentScreenY;
	private long lastUpdate;

	public CameraPositionCalculation(Player movedPlayer) {
		super();
		this.movedPlayer = movedPlayer;
		this.currentScreenX = 0;
		this.currentScreenY = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		updateScreenPosition();
	}

	void updateScreenPosition() {
		long currentTime = System.currentTimeMillis();
		if (!this.movedPlayer.isDead()) {
			immediateUpdateScreenPosition();
		} else {
			int delta = (int) ((this.lastUpdate - currentTime) / 5);
			smoothlyUpdateScreenPosition(delta);
		}
		this.lastUpdate = currentTime;
	}

	private void immediateUpdateScreenPosition() {
		this.currentScreenX = this.movedPlayer.getCenterX();
		this.currentScreenY = this.movedPlayer.getCenterY();
	}

	/**
	 * we want to smoothly move to the players position if he is dead. This will
	 * avoid fast jumps because of next spawnpoint.
	 */
	public void smoothlyUpdateScreenPosition(int delta) {
		int diffBetweenPlayerAndScreenX = (int) (-this.currentScreenX + this.movedPlayer.getCenterX());
		int diffBetweenPlayerAndScreenY = (int) (this.movedPlayer.getCenterY() - this.currentScreenY);
		int maxScrollValueX = (int) (SCROLLING_WHILE_PLAYER_IS_DEAD * delta * Math.signum(diffBetweenPlayerAndScreenX));
		int maxScrollValueY = (int) (SCROLLING_WHILE_PLAYER_IS_DEAD * delta * Math.signum(diffBetweenPlayerAndScreenY));
		if (Math.abs(diffBetweenPlayerAndScreenX) <= Math.abs(maxScrollValueX)) {
			this.currentScreenX = this.movedPlayer.getCenterX();
		} else {
			this.currentScreenX = this.currentScreenX - maxScrollValueX;
		}
		if (Math.abs(diffBetweenPlayerAndScreenY) <= Math.abs(maxScrollValueY)) {
			this.currentScreenY = this.movedPlayer.getCenterY();
		} else {
			this.currentScreenY = this.currentScreenY - maxScrollValueY;
		}
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
