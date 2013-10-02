package de.oetting.bumpingbunnies.usecases.game.android.input;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public abstract class AbstractControlledMovement implements InputService {

	private PlayerMovementController playerMovement;

	public AbstractControlledMovement(PlayerMovementController playerMovement) {
		this.playerMovement = playerMovement;
	}

	protected void reset() {
		this.playerMovement.removeMovement();
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
