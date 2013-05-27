package de.jumpnbump.usecases.game.android.input;

import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class AiInputService implements InputService {

	private final Player otherPlayer;
	private final Player player;
	private final PlayerMovementController playerMovement;
	private long counterToRememberMovement;
	private boolean lastMovementIsLeft;
	private int counter = 0;
	private boolean rememberMoveUp;

	public AiInputService(Player otherPlayer,
			PlayerMovementController playerMovement) {
		this.otherPlayer = otherPlayer;
		this.playerMovement = playerMovement;
		this.player = playerMovement.getPlayer();
	}

	@Override
	public void executeUserInput() {
		if (this.counterToRememberMovement > 0) {
			if (this.lastMovementIsLeft) {
				this.playerMovement.tryMoveLeft();
			} else {
				this.playerMovement.tryMoveRight();
			}
			if (this.rememberMoveUp) {
				this.playerMovement.tryMoveUp();
			}

			this.counterToRememberMovement--;
		} else {
			this.counter++;
			if (this.counter > 500) {
				this.counter = 0;
				this.counterToRememberMovement = 25;
				this.lastMovementIsLeft = moveLeft();
				this.rememberMoveUp = true;
			}
			if (!isAtSimilarWidth()) {
				if (moveLeft()) {
					this.playerMovement.tryMoveLeft();
				} else {
					this.playerMovement.tryMoveRight();
				}
			}
			if (!isFarOverOtherPlayer()) {
				this.playerMovement.tryMoveUp();
			} else {
				this.counterToRememberMovement = 50;
				this.lastMovementIsLeft = moveLeft();
				this.rememberMoveUp = false;
			}
		}
	}

	private boolean isFarOverOtherPlayer() {
		return this.player.getCenterY() < this.otherPlayer.getCenterY() - 0.5;
	}

	private boolean isAtSimilarWidth() {
		return Math.abs(this.player.getCenterX()
				- this.otherPlayer.getCenterX()) < 0.1;
	}

	private boolean moveLeft() {
		boolean otherPlayerIsOverMe = isOtherPlayerOverMe();
		if (otherPlayerIsOverMe) {
			if (atLeftBorderAndOtherPlayerClose()) {
				return false;
			}
			if (atRightBorderAndOtherPlayerClose()) {
				return true;
			}
		}

		boolean leftToOtherPlayer = this.otherPlayer.getCenterX() > this.player
				.getCenterX();
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

	private boolean atRightBorderAndOtherPlayerClose() {
		return this.player.getCenterX() > 0.1
				&& this.player.getCenterX() - this.otherPlayer.getCenterX() < 0.1;
	}

	private boolean atLeftBorderAndOtherPlayerClose() {
		return this.player.getCenterX() < 0.1
				&& this.otherPlayer.getCenterX() - this.player.getCenterX() < 0.1;
	}

	private boolean isOtherPlayerOverMe() {
		return this.otherPlayer.getCenterY() < this.player.getCenterY() + 0.1;
	}

	@Override
	public void destroy() {
	}

}
