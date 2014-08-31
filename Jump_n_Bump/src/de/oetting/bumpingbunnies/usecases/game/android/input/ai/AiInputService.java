package de.oetting.bumpingbunnies.usecases.game.android.input.ai;

import java.util.Random;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.factories.OpponentInput;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AiInputService implements OpponentInput {

	private static Logger LOGGER = LoggerFactory
			.getLogger(AiInputService.class);

	private Player otherPlayer;
	private final Player player;
	private final PlayerMovement playerMovement;
	private boolean rememberMoveLeft;
	private boolean rememberMoveRight;
	private int counter = 0;
	private boolean rememberMoveUp;
	private int nextdurationOfMovement = 0;
	private Random random;

	private final World world;

	public AiInputService(PlayerMovement playerMovement, World world) {
		this.playerMovement = playerMovement;
		this.world = world;
		this.player = playerMovement.getPlayer();
		this.random = new Random(System.currentTimeMillis());
		LOGGER.info("initialising ai for player %d", this.player.id());
	}

	@Override
	public void executeNextStep() {
		this.otherPlayer = findNearestOtherPlayer();
		this.counter++;
		if (this.counter < this.nextdurationOfMovement) {
			moveRememberedMovement();
		} else {
			reset();
			moveNormalMovement();
		}
	}

	private Player findNearestOtherPlayer() {
		double nearest = Double.MAX_VALUE;
		Player nearestPlayer = null;
		for (Player p : this.world.getAllPlayer()) {
			if (p.id() != this.player.id()) {
				double distance = distance(p, this.player);
				if (distance < nearest) {
					nearest = distance;
					nearestPlayer = p;
				}
			}
		}
		if (nearestPlayer != null) {
			return nearestPlayer;
		}
		LOGGER.warn("No nearest player found");
		return this.player;
	}

	private double distance(Player p1, Player p2) {
		return Math.pow(p1.centerX() - p2.centerX(), 2)
				+ Math.pow(p1.centerY() - p2.centerY(), 2);
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

	private boolean isAtSimilarWidth() {
		return Math.abs(this.player.getCenterX()
				- this.otherPlayer.getCenterX()) < 0.025;
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

}
