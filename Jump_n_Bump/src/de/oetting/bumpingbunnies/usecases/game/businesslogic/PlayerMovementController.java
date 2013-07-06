package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerMovementController implements ModelConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlayerMovementController.class);

	private final Player movedPlayer;
	private boolean movingUp;
	private final InteractionService interActionService;
	private final CollisionDetection collisionDetection;
	private final World world;
	boolean tryingToRemoveHorizontalMovement;

	private final int speedFactor;

	public PlayerMovementController(Player movedPlayer, World world,
			InteractionService interActionService,
			CollisionDetection collisionDetection, int speedFactor) {
		this.movedPlayer = movedPlayer;
		this.world = world;
		this.interActionService = interActionService;
		this.collisionDetection = collisionDetection;
		this.speedFactor = speedFactor;
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
		this.interActionService.interactWith(this.movedPlayer, this.world);
		this.movedPlayer.moveNextStep();
		this.movedPlayer.calculateNextSpeed();
	}

	private void conditionalSetJumpMovement() {
		if (this.movingUp) {
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
		this.movedPlayer.setAccelerationX(+findAccelerationForObject()
				* this.speedFactor);
	}

	private int findAccelerationForObject() {
		GameObject go = this.collisionDetection
				.findObjectThisPlayerIsCollidingWith(this.movedPlayer);
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

	private boolean isCollidingWithObject() {
		return this.collisionDetection
				.findObjectThisPlayerIsCollidingWith(this.movedPlayer) != null;
	}

	public void tryMoveLeft() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(-findAccelerationForObject()
				* this.speedFactor);
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
		LOGGER.verbose("removing vertical movement");
		this.movingUp = false;
	}

	public void tryMoveUp() {
		LOGGER.verbose("trying to move up");
		this.movingUp = true;
	}

	public void tryMoveDown() {
		LOGGER.verbose("trying to move down");
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
