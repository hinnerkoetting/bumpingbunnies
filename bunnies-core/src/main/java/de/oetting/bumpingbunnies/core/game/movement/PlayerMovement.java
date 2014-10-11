package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerMovement {

	private final Player movedPlayer;

	public PlayerMovement(Player movedPlayer) {
		this.movedPlayer = movedPlayer;
	}

	public void tryMoveRight() {
		this.movedPlayer.setMovingRight();
	}

	public void tryMoveLeft() {
		this.movedPlayer.setMovingLeft();
	}

	public void removeLeftMovement() {
		if (this.movedPlayer.getAccelerationX() < 0) {
			removeHorizontalMovement();
		}
	}

	public void removeRightMovement() {
		if (this.movedPlayer.getAccelerationX() > 0) {
			removeHorizontalMovement();
		}
	}

	public void removeHorizontalMovement() {
		this.movedPlayer.setNotMoving();
	}

	public void tryMoveUp() {
		movedPlayer.setJumping(true);
	}

	public void tryMoveDown() {
		movedPlayer.setJumping(false);
	}

	public void removeMovement() {
		removeHorizontalMovement();
		movedPlayer.setJumping(false);
	}

	public Player getPlayer() {
		return this.movedPlayer;
	}

}
