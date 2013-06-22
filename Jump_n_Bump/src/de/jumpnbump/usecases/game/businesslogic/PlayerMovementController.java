package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovementController implements ModelConstants {

	private static final MyLog LOGGER = Logger
			.getLogger(PlayerMovementController.class);

	private final Player movedPlayer;
	private boolean movingUp;
	private final InteractionService interActionService;
	private final CollisionDetection collisionDetection;
	private final World world;
	boolean tryingToRemoveHorizontalMovement;

	public PlayerMovementController(Player movedPlayer, World world,
			InteractionService interActionService,
			CollisionDetection collisionDetection) {
		this.movedPlayer = movedPlayer;
		this.world = world;
		this.interActionService = interActionService;
		this.collisionDetection = collisionDetection;
	}

	public void nextStep(long delta) {
		movePlayerNextStep(delta);
	}

	private void movePlayerNextStep(long delta) {
		for (int i = 0; i < delta; i++) {
			executeOneStep();
		}
	}

	private void executeOneStep() {
		computeGravity();
		this.interActionService.interactWith(this.movedPlayer, this.world);
		this.movedPlayer.moveNextStep();
		this.movedPlayer.calculateNextSpeed();
	}

	private void computeGravity() {
		if (this.movingUp) {
			this.movedPlayer
					.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
		} else {
			this.movedPlayer.setAccelerationY(ModelConstants.PLAYER_GRAVITY);
		}
		if (this.tryingToRemoveHorizontalMovement) {
			if (this.movedPlayer.movementX() != 0) {
				tryToSteerAgainstMovement();
			}
		}
	}

	private void tryToSteerAgainstMovement() {
		int breakAcceleration = (int) -Math
				.signum(this.movedPlayer.movementX())
				* findAccelerationForObject();
		if (Math.abs(this.movedPlayer.movementX()) <= Math
				.abs(breakAcceleration)) {
			this.movedPlayer.setMovementX(0);
			this.movedPlayer.setAccelerationX(0);
		} else {
			this.movedPlayer.setAccelerationX(breakAcceleration);
		}
	}

	public void tryMoveRight() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(+findAccelerationForObject());
	}

	private int findAccelerationForObject() {
		GameObject go = this.collisionDetection
				.findObjectThisPlayerIsStandingOn(this.movedPlayer);
		if (go == null) {
			LOGGER.verbose("Acceleration air %d",
					ModelConstants.ACCELERATION_X_AIR);
			return ModelConstants.ACCELERATION_X_AIR;
		} else {
			int ac = go.accelerationOnThisGround();
			LOGGER.verbose("Acceleration %d", ac);
			return ac;
		}
	}

	public void tryMoveLeft() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(-findAccelerationForObject());
		// this.movedPlayer.setMovementX(-MOVEMENT);
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
		this.tryingToRemoveHorizontalMovement = true;
		this.movedPlayer.setAccelerationX(0);
	}

	public void removeVerticalMovement() {
		this.movingUp = false;
	}

	public void tryMoveUp() {
		if (isStandingOnGround()) {
			this.movedPlayer.setMovementY(ModelConstants.PLAYER_JUMP_SPEED);
			this.movedPlayer.setAccelerationY(0);
		} else {
			this.movingUp = true;
		}
	}

	public void tryMoveDown() {
		this.movingUp = false;
	}

	public void removeMovement() {
		removeHorizontalMovement();
		this.movingUp = false;
		LOGGER.debug("removing movement");
	}

	public Player getPlayer() {
		return this.movedPlayer;
	}

	public Player isOnTopOfOtherPlayer() {
		return this.collisionDetection
				.findPlayerThisPlayerIsStandingOn(this.movedPlayer);
	}

	public boolean isJumping() {
		return this.movingUp;
	}

	public boolean isStandingOnGround() {
		return this.collisionDetection.objectStandsOnGround(this.movedPlayer);
	}

}
