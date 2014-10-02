package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.Water;

/**
 * Responsible for calculation the movement of players. Checks for collision, updates screen position.
 */
public class PlayerMovementCalculation {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMovementCalculation.class);

	private final Player movedPlayer;
	private final GameObjectInteractor interactionService;
	private final CollisionDetection collisionDetection;
	private final MusicPlayer jumpMusic;

	public PlayerMovementCalculation(Player movedPlayer, GameObjectInteractor interactionService,
			CollisionDetection collisionDetection, MusicPlayer jumpMusic) {
		super();
		this.movedPlayer = movedPlayer;
		this.interactionService = interactionService;
		this.collisionDetection = collisionDetection;
		this.jumpMusic = jumpMusic;
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
		computeMovement();
		this.interactionService.interactWith(this.movedPlayer);
		this.movedPlayer.moveNextStep();
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

	private void computeMovement() {
		computeVerticalMovement();
		computeHorizontalMovement();
		this.movedPlayer.calculateNextSpeed();
	}

	public void computeVerticalMovement() {
		if (this.movedPlayer.isJumpingButtonPressed()) {
			handleJumpButtonPressed();
		} else {
			if (standsOnFixedObject()) {
				this.movedPlayer.setAccelerationY(0);
			} else {
				this.movedPlayer.setAccelerationY(ModelConstants.PLAYER_GRAVITY);
			}
		}
	}

	private void handleJumpButtonPressed() {
		if (standsOnFixedObject()) {
			setJumpMovement();
			this.jumpMusic.start();
		} else if (isInWater()) {
			this.movedPlayer
					.setMovementY(ModelConstants.PLAYER_JUMP_SPEED_WATER);
			this.movedPlayer.setAccelerationY(0);
		} else {
			this.movedPlayer
					.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
		}
	}

	public void computeHorizontalMovement() {
		if (isPlayerMoving()) {
			int accelerationX = findAccelerationForObject();
			boolean isMovingLeft = this.movedPlayer.isMovingLeft();
			this.movedPlayer.setAccelerationX(isMovingLeft ? -accelerationX : accelerationX);
		} else {
			if (this.movedPlayer.movementX() != 0) {
				steerAgainstMovement();
			} else {
				this.movedPlayer.setAccelerationX(0);
			}
		}
	}

	public boolean isPlayerMoving() {
		return !this.movedPlayer.isTryingToRemoveHorizontalMovement();
	}

	void steerAgainstMovement() {
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

	public boolean controlsThisPlayer(Player movedPlayer) {
		return this.movedPlayer.equals(movedPlayer);
	}
}
