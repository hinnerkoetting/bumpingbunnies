package de.oetting.bumpingbunnies.core.input;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public abstract class AbstractControlledMovement implements InputService {

	private Player playerMovement;

	public AbstractControlledMovement(Player playerMovement) {
		this.playerMovement = playerMovement;
	}

	protected void reset() {
		this.playerMovement.setNotMoving();
	}

	protected Player getMovedPlayer() {
		return this.playerMovement;
	}

	protected void moveUp() {
		this.playerMovement.setJumping(true);
	}

	protected void moveLeft() {
		this.playerMovement.setMovingLeft();
	}

	protected void moveRight() {
		this.playerMovement.setMovingRight();
	}

	protected void moveDown() {
		this.playerMovement.setJumping(false);
	}

	protected void removeHorizontalMovement() {
		this.playerMovement.setNotMoving();
	}

	protected boolean touchesPlayerThisVerticalPosition(double yPosition) {
		Player movedPlayer = getMovedPlayer();
		return movedPlayer.maxY() > yPosition && movedPlayer.minY() < yPosition;
	}

	protected boolean touchesPlayerThisHorizontalPosition(double xPosition) {
		Player movedPlayer = getMovedPlayer();
		return movedPlayer.maxX() > xPosition && movedPlayer.minX() < xPosition;
	}
}
