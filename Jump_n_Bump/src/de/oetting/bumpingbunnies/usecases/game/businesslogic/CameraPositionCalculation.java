package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CameraPositionCalculation {

	protected static final int SCROLLING_WHILE_PLAYER_IS_DEAD = ModelConstants.STANDARD_WORLD_SIZE / 200;
	private final Player movedPlayer;

	private int currentScreenX;
	private int currentScreenY;
	private long lastUpdate;

	public CameraPositionCalculation(Player movedPlayer) {
		super();
		this.movedPlayer = movedPlayer;
		this.currentScreenX = 0;
		this.currentScreenY = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	public void updateScreenPosition() {
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
		this.movedPlayer.setCurrentScreenX(this.movedPlayer.getCenterX());
		this.movedPlayer.setCurrentScreenY(this.movedPlayer.getCenterY());
	}

	/**
	 * we want to smoothly move to the players position if he is dead. This will avoid fast jumps because of next spawnpoint.
	 */
	protected void smoothlyUpdateScreenPosition(int delta) {
		int diffBetweenPlayerAndScreenX = (int) (-
				this.movedPlayer.getCurrentScreenX() + this.movedPlayer.getCenterX());
		int diffBetweenPlayerAndScreenY = (int) (this.movedPlayer.getCenterY() - this.movedPlayer.getCurrentScreenY());
		int maxScrollValueX = (int) (SCROLLING_WHILE_PLAYER_IS_DEAD * delta
				* Math.signum(diffBetweenPlayerAndScreenX));
		int maxScrollValueY = (int) (SCROLLING_WHILE_PLAYER_IS_DEAD * delta
				* Math.signum(diffBetweenPlayerAndScreenY));
		if (Math.abs(diffBetweenPlayerAndScreenX) <= Math.abs(maxScrollValueX)) {
			this.movedPlayer.setCurrentScreenX(this.movedPlayer.getCenterX());
		} else {
			this.movedPlayer.setCurrentScreenX(this.movedPlayer.getCurrentScreenX() - maxScrollValueX);
		}
		if (Math.abs(diffBetweenPlayerAndScreenY) <= Math.abs(maxScrollValueY)) {
			this.movedPlayer.setCurrentScreenY(this.movedPlayer.getCenterY());
		} else {
			this.movedPlayer.setCurrentScreenY(this.movedPlayer.getCurrentScreenY() - maxScrollValueY);
		}
	}
}
