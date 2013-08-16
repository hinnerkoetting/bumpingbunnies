package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

/**
 * Responsible for calculation the movement of players. Checks for collision, updates screen position.
 */
public class PlayerMovementCalculation {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMovementCalculation.class);

	private final Player movedPlayer;
	private final InteractionService interactionService;
	private final PlayerState movedPlayerState;
	private final CollisionDetection collisionDetection;

	public PlayerMovementCalculation(Player movedPlayer, InteractionService interactionService, PlayerState movedPlayerState,
			CollisionDetection collisionDetection) {
		super();
		this.movedPlayer = movedPlayer;
		this.interactionService = interactionService;
		this.movedPlayerState = movedPlayerState;
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
		conditionalSetJumpMovement();
		this.interactionService.interactWith(this.movedPlayer);
		this.movedPlayer.moveNextStep();
		this.movedPlayer.calculateNextSpeed();
	}

	private void conditionalSetJumpMovement() {
		if (this.movedPlayerState.isJumpingButtonPressed()) {
			if (standsOnFixedObject()) {
				setJumpMovement();
			} else if (isInWater()) {
				this.movedPlayer
						.setMovementY(ModelConstants.PLAYER_JUMP_SPEED_WATER);
				this.movedPlayer.setAccelerationY(0);
			}
		}
	}

	private boolean isInWater() {
		GameObject collidingObject = this.collisionDetection
				.findObjectThisPlayerIsCollidingWith(this.movedPlayer);
		if (collidingObject != null) {
			return collidingObject instanceof Water;
		}
		return false;
	}

	private boolean standsOnFixedObject() {
		GameObject collidingObject = this.collisionDetection
				.findObjectThisPlayerIsStandingOn(this.movedPlayer);
		if (collidingObject != null) {
			return !(collidingObject instanceof Water);
		}
		return false;
	}

	private void setJumpMovement() {
		this.movedPlayer.setMovementY(ModelConstants.PLAYER_JUMP_SPEED);
		this.movedPlayer.setAccelerationY(0);
	}

	private void computeGravity() {
		computeVerticalGravity();
		computeHorizontalGravity();
	}

	public void computeVerticalGravity() {
		if (this.movedPlayerState.isJumpingButtonPressed()) {
			this.movedPlayer
					.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
		} else {
			this.movedPlayer.setAccelerationY(ModelConstants.PLAYER_GRAVITY);
		}
	}

	public void computeHorizontalGravity() {
		if (isPlayerMoving()) {
			int accelerationX = findAccelerationForObject();
			boolean isMovingLeft = this.movedPlayer.isMovingLeft();
			this.movedPlayer.setAccelerationX(isMovingLeft ? -accelerationX : accelerationX);
		} else {
			if (this.movedPlayer.movementX() != 0) {
				tryToSteerAgainstMovement();
			} else {
				this.movedPlayer.setAccelerationX(0);
			}
		}
	}

	public boolean isPlayerMoving() {
		return !this.movedPlayer.isTryingToRemoveHorizontalMovement();
	}

	private void tryToSteerAgainstMovement() {
		int breakAcceleration = (int) -Math
				.signum(this.movedPlayer.movementX())
				* findAccelerationForObject();
		if (Math.abs(this.movedPlayer.movementX()) <= Math
				.abs(breakAcceleration) * Math.pow(this.movedPlayer.getSpeedFaktor(), 2)) {
			this.movedPlayer.setMovementX(0);
			this.movedPlayer.setAccelerationX(0);
		} else {
			this.movedPlayer.setAccelerationX(breakAcceleration);
		}
	}

	private int findAccelerationForObject() {
		GameObject go = this.collisionDetection
				.findObjectThisPlayerIsStandingOn(this.movedPlayer);
		if (go == null) {
			LOGGER.verbose("Acceleration air %d",
					ModelConstants.ACCELERATION_X_AIR);
			if (isInWater()) {
				return ModelConstants.ACCELERATION_X_WATER;
			}
			return ModelConstants.ACCELERATION_X_AIR;
		} else {
			int ac = go.accelerationOnThisGround();
			LOGGER.verbose("Acceleration %d", ac);
			return ac;
		}

	}
}
