package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerMovementController implements ModelConstants {

	private static final MyLog LOGGER = Logger
			.getLogger(PlayerMovementController.class);

	private final Player movedPlayer;
	private boolean movingUp;
	private InteractionService interActionService;

	private final World world;

	// private final CollisionDetection collision;

	public PlayerMovementController(Player movedPlayer, World world,
			InteractionService interActionService) {
		this.movedPlayer = movedPlayer;
		this.world = world;
		this.interActionService = interActionService;
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
		// if (this.collision.objectStandsOnGround(this.movedPlayer)) {
		// this.movedPlayer.setMovementY(ModelConstants.PLAYER_JUMP_SPEED);
		// this.movedPlayer.setAccelerationY(0);
		// } else {
		// this.movingUp = true;
		// }
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
		return null;
		// return
		// this.collision.playerStandOnTopOfOtherPlayer(this.movedPlayer);
	}

	public boolean isJumping() {
		return this.movingUp;
	}

	public boolean isStandingOnGround() {
		return false;
		// return this.collision.objectStandsOnGround(this.movedPlayer);
	}

}
