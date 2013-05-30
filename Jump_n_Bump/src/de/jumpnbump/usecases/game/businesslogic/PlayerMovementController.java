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
		computeGravity();
		movePlayerNextStep(delta);
	}

	private void movePlayerNextStep(long delta) {
		for (int i = 0; i < delta; i++) {
			executeOneStep();
		}
	}

	private void executeOneStep() {
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
			this.movedPlayer.setAccelerationX((int) -Math
					.signum(this.movedPlayer.movementX())
					* ModelConstants.ACCELERATION_X_WALL);
		}
	}

	public void tryMoveRight() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(+findAccelerationForObject());
		// this.movedPlayer.setMovementX(MOVEMENT);
	}

	private int findAccelerationForObject() {
		GameObject go = this.collisionDetection
				.findObjectThisPlayerIsStandingOn(this.movedPlayer);
		if (go == null) {
			return ModelConstants.ACCELERATION_X_AIR;
		} else {
			return go.accelerationOnThisGround();
		}
	}

	public void tryMoveLeft() {
		this.tryingToRemoveHorizontalMovement = false;
		this.movedPlayer.setAccelerationX(-findAccelerationForObject());
		// this.movedPlayer.setMovementX(-MOVEMENT);
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
