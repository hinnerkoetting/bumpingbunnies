package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerMovement {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMovement.class);

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
		LOGGER.verbose("trying to move up");
		this.movedPlayer.getState().setJumpingButtonPressed(true);
	}

	public void tryMoveDown() {
		LOGGER.verbose("trying to move down");
		this.movedPlayer.getState().setJumpingButtonPressed(false);
	}

	public void removeMovement() {
		removeHorizontalMovement();
		LOGGER.debug("removing movement");
		this.movedPlayer.getState().setJumpingButtonPressed(false);
	}

	public Player getPlayer() {
		return this.movedPlayer;
	}

}
