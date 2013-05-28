package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerMovementController implements ModelConstants {

	private static final MyLog LOGGER = Logger
			.getLogger(PlayerMovementController.class);

	private final Player movedPlayer;
	private final CollisionDetection collision;
	private boolean movingUp;

	public PlayerMovementController(Player movedPlayer,
			CollisionDetection collision) {
		this.movedPlayer = movedPlayer;
		this.collision = collision;
	}

	public void nextStep(long delta) {
		computeGravity();
		movePlayerNextStep(delta);
	}

	private void movePlayerNextStep(long delta) {
		for (int i = 0; i < delta; i++) {
			executeOneStep();
		}
	}

	private void executeOneStep() {
		if (!this.collision.willCollideVertical(this.movedPlayer)) {
			this.movedPlayer.moveNextStepY();
		} else {
			this.movedPlayer.setMovementY(0);
			LOGGER.debug("Collision Vertical");
		}
		if (!this.collision.willCollideHorizontal(this.movedPlayer)) {
			this.movedPlayer.moveNextStepX();
		} else {
			this.movedPlayer.setMovementX(0);
			LOGGER.debug("Collision horizontal");
		}
		this.movedPlayer.calculateNextSpeed();
	}

	private void computeGravity() {
		if (this.movingUp) {
			this.movedPlayer
					.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
		} else {
			this.movedPlayer.setAccelerationY(ModelConstants.PLAYER_GRAVITY);
		}
	}

	public void tryMoveRight() {
		this.movedPlayer.setMovementX(MOVEMENT);
	}

	public void tryMoveLeft() {
		this.movedPlayer.setMovementX(-MOVEMENT);
	}

	public void removeHorizontalMovement() {
		this.movedPlayer.setMovementX(0);
	}

	public void removeVerticalMovement() {
		this.movingUp = false;
	}

	public void tryMoveUp() {
		if (this.collision.objectStandsOnGround(this.movedPlayer)) {
			this.movedPlayer.setMovementY(ModelConstants.PLAYER_JUMP_SPEET);
			this.movedPlayer.setAccelerationY(0);
		} else {
			this.movingUp = true;
		}
	}

	public void tryMoveDown() {
		this.movingUp = false;
	}

	public void removeMovement() {
		this.movedPlayer.setMovementX(0);
		this.movingUp = false;
		LOGGER.debug("removing movement");
	}

	public Player getPlayer() {
		return this.movedPlayer;
	}

	public Player isOnTopOfOtherPlayer() {
		return this.collision.playerStandOnTopOfOtherPlayer(this.movedPlayer);
	}

}
