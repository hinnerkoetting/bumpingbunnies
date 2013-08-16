package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerMovementController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMovementController.class);

	private final Player movedPlayer;

	public PlayerMovementController(Player movedPlayer,
			InteractionService interActionService,
			CollisionDetection collisionDetection) {
		this.movedPlayer = movedPlayer;
	}

	public void tryMoveRight() {
		this.movedPlayer.setTryingToRemoveHorizontalMovement(false);
		this.movedPlayer.setMovingRight();
	}

	public void tryMoveLeft() {
		this.movedPlayer.setTryingToRemoveHorizontalMovement(false);
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
		this.movedPlayer.setTryingToRemoveHorizontalMovement(true);
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
