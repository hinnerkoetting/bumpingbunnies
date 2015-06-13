package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.Water;

/**
 * Responsible for calculation the movement of players. Checks for collision,
 * updates screen position.
 */
public class BunnyMovement {

	private static final Logger LOGGER = LoggerFactory.getLogger(BunnyMovement.class);

	private final Bunny movedPlayer;
	private final GameObjectInteractor objectInteraction;
	private final CollisionDetection collisionDetection;
	private final MusicPlayer jumpMusic;

	public BunnyMovement(Bunny movedPlayer, GameObjectInteractor interactionService,
			CollisionDetection collisionDetection, MusicPlayer jumpMusic) {
		this.movedPlayer = movedPlayer;
		this.objectInteraction = interactionService;
		this.collisionDetection = collisionDetection;
		this.jumpMusic = jumpMusic;
	}

	public void nextStep(long delta) {
		movePlayerNextStep(delta);
		movedPlayer.setInWater(isInWater());
	}

	private void movePlayerNextStep(long delta) {
		for (int i = 0; i < delta; i++) {
			executeOneStep();
		}
	}

	private void executeOneStep() {
		computeMovement();
		this.objectInteraction.interactWith(this.movedPlayer);
		this.movedPlayer.moveNextStep();
	}

	private boolean isInWater() {
		GameObject collidingObject = findCollidingObject();
		if (collidingObject != null) {
			return collidingObject instanceof Water;
		}
		return false;
	}


	private GameObject findCollidingObject() {
		return this.collisionDetection.findObjectThisPlayerIsCollidingWith(this.movedPlayer);
	}

	private boolean standsOnFixedObject(int segmentIndex) {
		GameObject collidingObject = this.collisionDetection.findObjectThisPlayerIsStandingOn(segmentIndex, this.movedPlayer);
		if (collidingObject != null) {
			return !(collidingObject instanceof Water);
		}
		return false;
	}

	private void computeMovement() {
		int segmentIndex = findSegmentThatBunnyBelongsTo();
		computeVerticalMovement(segmentIndex);
		computeHorizontalMovement(segmentIndex);
		this.movedPlayer.calculateNextSpeed();
	}

	private int findSegmentThatBunnyBelongsTo() {
		return collisionDetection.getSegmentThatBunnyBelongsTo(movedPlayer);
	}

	public void computeVerticalMovement(int segmentIndex) {
		movedPlayer.setAccelerationY(computeVerticalAcceleration(segmentIndex));
		if (this.movedPlayer.isJumpingButtonPressed()) {
			computeMovementYForJumpingBunny(segmentIndex);
		}
	}

	private int computeVerticalAcceleration(int segmentIndex) {
		if (standsOnFixedObject(segmentIndex))
			return 0;
		else {
			if (this.movedPlayer.isJumpingButtonPressed()) {
				return ModelConstants.BUNNY_GRAVITY_WHILE_JUMPING;
			}
			return ModelConstants.BUNNY_GRAVITY;
		}
	}

	private void computeMovementYForJumpingBunny(int segmentIndex) {
		if (standsOnFixedObject(segmentIndex)) {
			this.movedPlayer.setMovementY(ModelConstants.BUNNY_JUMP_SPEED);
			this.jumpMusic.start();
		} else if (isInWater()) {
			this.movedPlayer.setMovementY(ModelConstants.BUNNY_JUMP_SPEED_WATER);
		}
	}

	public void computeHorizontalMovement(int segmentIndex) {
		if (isPlayerMoving()) {
			int accelerationX = findAccelerationForObject(segmentIndex);
			this.movedPlayer.setAccelerationX(movedPlayer.isMovingLeft() ? -accelerationX : accelerationX);
		} else {
			if (this.movedPlayer.movementX() != 0) {
				steerAgainstMovement(segmentIndex);
			} else {
				this.movedPlayer.setAccelerationX(0);
			}
		}
	}

	public boolean isPlayerMoving() {
		return !this.movedPlayer.isTryingToRemoveHorizontalMovement();
	}

	void steerAgainstMovement(int segmentIndex) {
		int breakAcceleration = (int) -Math.signum(this.movedPlayer.movementX()) * findAccelerationForObject(segmentIndex);
		if (Math.abs(this.movedPlayer.movementX()) <= Math.abs(breakAcceleration)
				* Math.pow(this.movedPlayer.getSpeedFaktor(), 2)) {
			this.movedPlayer.setMovementX(0);
			this.movedPlayer.setAccelerationX(0);
		} else {
			this.movedPlayer.setAccelerationX(breakAcceleration);
		}
	}

	private int findAccelerationForObject(int segmentIndex) {
		GameObject go = this.collisionDetection.findObjectThisPlayerIsStandingOn(segmentIndex, this.movedPlayer);
		if (go == null) {
			LOGGER.verbose("Acceleration air %d", ModelConstants.ACCELERATION_X_AIR);
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

	public boolean controlsThisPlayer(Bunny movedPlayer) {
		return this.movedPlayer.equals(movedPlayer);
	}
}
