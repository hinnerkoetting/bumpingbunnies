package de.jumpnbump.usecases.game.android.input.ai;

import java.util.Random;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class AiInputService implements InputService {

	private static MyLog LOGGER = Logger.getLogger(AiInputService.class);

	private final Player otherPlayer;
	private final Player player;
	private final PlayerMovementController playerMovement;
	private boolean rememberMoveLeft;
	private boolean rememberMoveRight;
	private int counter = 0;
	private boolean rememberMoveUp;
	private int nextdurationOfMovement = 0;
	private Random random;

	public AiInputService(Player otherPlayer,
			PlayerMovementController playerMovement) {
		this.otherPlayer = otherPlayer;
		this.playerMovement = playerMovement;
		this.player = playerMovement.getPlayer();
		this.random = new Random(System.currentTimeMillis());
		LOGGER.info("initialising ai for player %d", this.player.id());
	}

	@Override
	public void executeUserInput() {
		this.counter++;
		if (this.counter < this.nextdurationOfMovement) {
			moveRememberedMovement();
		} else {
			reset();
			moveNormalMovement();
		}
	}

	private void reset() {
		this.rememberMoveRight = false;
		this.rememberMoveLeft = false;
		this.rememberMoveUp = false;
		this.nextdurationOfMovement = this.random.nextInt(10);
		this.counter = 0;
	}

	private void moveNormalMovement() {
		if (isAtSimilarWidth()) {
			if (isOtherPlayerOverMe()) {
				this.nextdurationOfMovement = 25;
				runAway();
			} else {
				runTowardsOtherPlayer();
			}
		} else {
			if (shouldIBreakOutOfSurrounding()) {
				if (this.player.getCenterX() < 0.5) {
					doMoveRight();
				} else {
					doMoveLeft();
				}
			} else {
				chaseOtherPlayer();
				if (isOtherPlayerOverMe()) {
					moveUp();
				} else {
				}
			}
		}
	}

	private boolean shouldIBreakOutOfSurrounding() {
		boolean otherPlayerIsOverMe = isOtherPlayerOverMe();
		if (otherPlayerIsOverMe) {
			if (atLeftBorderAndOtherPlayerClose()
					|| atRightBorderAndOtherPlayerClose()) {
				return true;
			}
		}
		return false;
	}

	private void runAway() {
		if (isLeftToOtherPlayer()) {
			doMoveLeft();
		} else {
			doMoveRight();
		}
	}

	private void doMoveLeft() {
		this.playerMovement.tryMoveLeft();
		this.rememberMoveLeft = true;
		this.rememberMoveRight = false;
	}

	private void doMoveRight() {
		this.playerMovement.tryMoveRight();
		this.rememberMoveLeft = false;
		this.rememberMoveRight = true;
	}

	private void moveUp() {
		this.playerMovement.tryMoveUp();
		this.rememberMoveUp = true;
	}

	private void runTowardsOtherPlayer() {
		if (isLeftToOtherPlayer()) {
			this.playerMovement.tryMoveRight();
		} else {
			this.playerMovement.tryMoveLeft();
		}
	}

	private void chaseOtherPlayer() {
		if (isLeftToOtherPlayer()) {
			doMoveRight();
		} else {
			doMoveLeft();
		}
	}

	private void moveRememberedMovement() {
		if (this.rememberMoveLeft) {
			this.playerMovement.tryMoveLeft();
		}
		if (this.rememberMoveRight) {
			this.playerMovement.tryMoveRight();
		}
		if (this.rememberMoveUp) {
			this.playerMovement.tryMoveUp();
		}
	}

	private boolean isFarOverOtherPlayer() {
		return this.player.getCenterY() < this.otherPlayer.getCenterY() - 0.3;
	}

	private boolean isFarUnderOtherPlayer() {
		return this.otherPlayer.getCenterY() < this.player.getCenterY() - 0.3;
	}

	private boolean isAtSimilarWidth() {
		return Math.abs(this.player.getCenterX()
				- this.otherPlayer.getCenterX()) < 0.025;
	}

	private boolean moveLeft() {
		boolean otherPlayerIsOverMe = false;
		// isOtherPlayerOverMe();
		// if (otherPlayerIsOverMe) {
		// if (isFarUnderOtherPlayer()) {
		// if (atLeftBorderAndOtherPlayerClose()) {
		// return false;
		// }
		// if (atRightBorderAndOtherPlayerClose()) {
		// return true;
		// }
		// }
		// }

		boolean leftToOtherPlayer = isLeftToOtherPlayer();
		boolean rightToOtherPlayer = !leftToOtherPlayer;
		boolean otherPlayerIsUnderMe = !otherPlayerIsOverMe;
		if (leftToOtherPlayer && otherPlayerIsOverMe) {
			return false;
		} else if (leftToOtherPlayer && otherPlayerIsUnderMe) {
			return false;
		} else if (rightToOtherPlayer && otherPlayerIsUnderMe) {
			return true;
		} else {
			return true;
		}

	}

	private boolean isLeftToOtherPlayer() {
		return this.otherPlayer.getCenterX() > this.player.getCenterX();
	}

	private boolean atRightBorderAndOtherPlayerClose() {
		return this.player.getCenterX() > 0.9
				&& Math.abs(this.player.getCenterX()
						- this.otherPlayer.getCenterX()) < 0.1;
	}

	private boolean atLeftBorderAndOtherPlayerClose() {
		return this.player.getCenterX() < 0.1
				&& Math.abs(this.otherPlayer.getCenterX()
						- this.player.getCenterX()) < 0.1;
	}

	private boolean isOtherPlayerOverMe() {
		return this.player.getCenterY() < this.otherPlayer.getCenterY() + 0.1;
	}

	@Override
	public void destroy() {
	}

}
