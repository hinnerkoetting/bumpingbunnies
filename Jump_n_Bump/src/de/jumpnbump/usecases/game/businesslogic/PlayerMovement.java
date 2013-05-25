package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerMovement {

	private static final MyLog LOGGER = Logger.getLogger(PlayerMovement.class);
	private static final double MOVEMENT = 0.001f;
	private final Player movedPlayer;
	private final CollisionDetection collision;
	private boolean movingUp;

	public PlayerMovement(Player movedPlayer, CollisionDetection collision) {
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
			LOGGER.debug("Collision Vertical");
		}
		if (!this.collision.willCollideHorizontal(this.movedPlayer)) {
			this.movedPlayer.moveNextStepX();
		} else {
			LOGGER.debug("Collision horizontal");
		}
		this.movedPlayer.calculateNextSpeed();
	}

	private void computeGravity() {
		if (this.movingUp) {
			this.movedPlayer.setAccelerationY(+0.000005f);
		} else {
			this.movedPlayer.setAccelerationY(+0.00001f);
		}
	}

	public void tryMoveRight() {
		this.movedPlayer.setMovementX(MOVEMENT);
	}

	public void tryMoveLeft() {
		this.movedPlayer.setMovementX(-MOVEMENT);
	}

	public void tryMoveUp() {
		if (this.collision.objectStandsOnGround(this.movedPlayer)) {
			this.movedPlayer.setMovementY(-0.0025);
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
	}

}
