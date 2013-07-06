package de.oetting.bumpingbunnies.usecases.game.android.input;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public abstract class AbstractControlledMovement implements InputService {

	private PlayerMovementController playerMovement;

	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean moveDown;

	public AbstractControlledMovement(PlayerMovementController playerMovement) {
		this.playerMovement = playerMovement;
	}

	protected void rememberMoveLeft() {
		this.moveRight = false;
		this.moveLeft = true;
	}

	protected void rememberMoveRight() {
		this.moveLeft = false;
		this.moveRight = true;
	}

	protected void rememberMoveUp() {
		this.moveDown = false;
		this.moveUp = true;
	}

	protected void rememberMoveDown() {
		this.moveUp = false;
		this.moveDown = true;
	}

	protected void reset() {
		this.moveDown = false;
		this.moveUp = false;
		this.moveLeft = false;
		this.moveRight = false;
		this.playerMovement.removeMovement();
	}

	protected void executeRememberedMovement() {
		if (this.moveDown) {
			this.playerMovement.tryMoveDown();
		} else if (this.moveUp) {
			this.playerMovement.tryMoveUp();
		}
		if (this.moveLeft) {
			this.playerMovement.tryMoveLeft();
		} else if (this.moveRight) {
			this.playerMovement.tryMoveRight();
		}
	}

	protected PlayerMovementController getPlayerMovement() {
		return this.playerMovement;
	}

	protected Player getMovedPlayer() {
		return this.playerMovement.getPlayer();
	}

	protected void moveUp() {
		this.playerMovement.tryMoveUp();
	}

	protected void moveLeft() {
		this.playerMovement.tryMoveLeft();
	}

	protected void moveRight() {
		this.playerMovement.tryMoveRight();
	}

	protected void moveDown() {
		this.playerMovement.tryMoveDown();
	}

	protected void removeHorizontalMovement() {
		this.moveLeft = false;
		this.moveRight = false;
		this.playerMovement.removeHorizontalMovement();
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
