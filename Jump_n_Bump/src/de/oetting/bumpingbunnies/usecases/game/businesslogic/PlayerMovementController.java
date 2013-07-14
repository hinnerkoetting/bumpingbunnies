package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerMovementController implements ModelConstants {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlayerMovementController.class);
	private static final long SCROLLING_WHILE_PLAYER_IS_DEAD = ModelConstants.STANDARD_WORLD_SIZE / 100;

	private final Player movedPlayer;
	private final PlayerState movedPlayerState;
	// private boolean movingUp;
	private final InteractionService interActionService;
	private final CollisionDetection collisionDetection;
	private final World world;
	boolean tryingToRemoveHorizontalMovement;

	public PlayerMovementController(Player movedPlayer, World world,
			InteractionService interActionService,
			CollisionDetection collisionDetection) {
		this.movedPlayer = movedPlayer;
		this.movedPlayerState = movedPlayer.getState();
		this.world = world;
		this.interActionService = interActionService;
		this.collisionDetection = collisionDetection;
	}

	public void nextStep(long delta) {
		movePlayerNextStep(delta);
		if (!this.movedPlayer.isDead()) {
			updateScreenPosition();
		} else {
			smoothlyUpdateScreenPosition(delta);
		}
	}

	/**
	 * we want to smoothly move to the players position if he is dead. This will
	 * avoid fast jumps because of next spawnpoint.
	 */
	private void smoothlyUpdateScreenPosition(long delta) {
		long diffBetweenPlayerAndScreenX = +this.movedPlayer.getCenterX() - this.movedPlayer.getCurrentScreenX();
		long diffBetweenPlayerAndScreenY = +this.movedPlayer.getCenterY() - this.movedPlayer.getCurrentScreenY();
		long maxScrollValueX = SCROLLING_WHILE_PLAYER_IS_DEAD * delta * (long) Math.signum(diffBetweenPlayerAndScreenX);
		long maxScrollValueY = SCROLLING_WHILE_PLAYER_IS_DEAD * delta * (long) Math.signum(diffBetweenPlayerAndScreenY);
		long xScrollValue = Math.abs(diffBetweenPlayerAndScreenX) > Math.abs(maxScrollValueX) ? maxScrollValueX
				: diffBetweenPlayerAndScreenX;
		long yScrollValue = Math.abs(diffBetweenPlayerAndScreenY) > Math.abs(maxScrollValueY) ? maxScrollValueY
				: diffBetweenPlayerAndScreenY;
		this.movedPlayer.setCurrentScreenX(this.movedPlayer.getCurrentScreenX() + xScrollValue);
		this.movedPlayer.setCurrentScreenY(this.movedPlayer.getCurrentScreenY() + yScrollValue);
	}

	private void updateScreenPosition() {
		this.movedPlayer.setCurrentScreenX(this.movedPlayer.getCenterX());
		this.movedPlayer.setCurrentScreenY(this.movedPlayer.getCenterY());
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
		if (this.movedPlayerState.isJumpingButtonPressed()) {
			this.movedPlayer
					.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING);
		} else {
			this.movedPlayer.setAccelerationY(ModelConstants.PLAYER_GRAVITY);
		}
		if (this.tryingToRemoveHorizontalMovement) {
			if (this.movedPlayer.movementX() != 0) {
				tryToSteerAgainstMovement();
			} else {
				this.movedPlayer.setAccelerationX(0);
			}
		}
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

	public void tryMoveRight() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(+findAccelerationForObject());
		this.movedPlayer.setFacingLeft(false);
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

	public void tryMoveLeft() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(-findAccelerationForObject());
		this.movedPlayer.setFacingLeft(true);
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
		this.movedPlayerState.setJumpingButtonPressed(false);
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

	public Player isOnTopOfOtherPlayer() {
		return this.collisionDetection
				.findPlayerThisPlayerIsStandingOn(this.movedPlayer);
	}

	public boolean isStandingOnGround() {
		return this.collisionDetection.objectStandsOnGround(this.movedPlayer);
	}

}
