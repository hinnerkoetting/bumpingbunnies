package de.oetting.bumpingbunnies.core.input;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public abstract class AbstractControlledMovement implements InputService {

	private Bunny playerMovement;

	public AbstractControlledMovement(Bunny playerMovement) {
		this.playerMovement = playerMovement;
	}

	protected void reset() {
		this.playerMovement.setNotMoving();
	}

	protected Bunny getMovedPlayer() {
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
		Bunny movedPlayer = getMovedPlayer();
		return movedPlayer.maxY() > yPosition && movedPlayer.minY() < yPosition;
	}

	protected boolean touchesPlayerThisHorizontalPosition(double xPosition) {
		Bunny movedPlayer = getMovedPlayer();
		return movedPlayer.maxX() > xPosition && movedPlayer.minX() < xPosition;
	}
}
