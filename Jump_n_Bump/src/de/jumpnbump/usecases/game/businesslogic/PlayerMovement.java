package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovement {

	private static final MyLog LOGGER = Logger.getLogger(PlayerMovement.class);
	private static final int MOVEMENT = 10;
	private final World world;
	private final Player movedPlayer;
	private final CollisionDetection collision;

	public PlayerMovement(World world, Player movedPlayer,
			CollisionDetection collision) {
		this.world = world;
		this.movedPlayer = movedPlayer;
		this.collision = collision;
	}

	public void nextStep() {
		computeGravity();
		movePlayerNextStep();
	}

	private void movePlayerNextStep() {
		if (!this.collision.willCollideVertical(this.movedPlayer)) {
			this.movedPlayer.moveNextStepY();
		}
		if (!this.collision.willCollideHorizontal(this.movedPlayer)) {
			this.movedPlayer.moveNextStepX();
		}
	}

	private void computeGravity() {
		if (this.collision.objectStandsOnGround(this.movedPlayer)) {
			LOGGER.debug("Standing on ground");
			if (this.movedPlayer.movementY() > 0) {
				this.movedPlayer.setMovementY(0);
			}
		} else {
			LOGGER.debug("In the air");
			this.movedPlayer.increaseYMovement(0.5f);
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
			this.movedPlayer.increaseYMovement(-20);
		} else {
			this.movedPlayer.increaseYMovement(-0.25f);
		}
	}

	public void tryMoveDown() {
	}

	public void removeMovement() {
		this.movedPlayer.setMovementX(0);
	}

}
