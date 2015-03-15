package de.oetting.bumpingbunnies.core.input.ai;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class AiInputService implements OpponentInput {

	private static Logger LOGGER = LoggerFactory.getLogger(AiInputService.class);

	private Player otherPlayer;
	private final Player player;
	private final PlayerMovement playerMovement;
	private boolean rememberMoveLeft;
	private boolean rememberMoveRight;
	private int counter = 0;
	private boolean rememberMoveUp;
	private long nextMovementDuration = 0;
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
	public void executeNextStep(long deltaSinceLastLast) {
		this.otherPlayer = findNearestOtherPlayer();
		this.counter += deltaSinceLastLast;
		if (this.counter > this.nextMovementDuration) {
			reset();
			computeNextMovement();
		}
		moveRememberedMovement();
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
		return Math.pow(p1.centerX() - p2.centerX(), 2) + Math.pow(p1.centerY() - p2.centerY(), 2);
	}

	private void reset() {
		this.rememberMoveRight = false;
		this.rememberMoveLeft = false;
		this.rememberMoveUp = false;
		this.nextMovementDuration = generateRandom(TimeUnit.MILLISECONDS.toMillis(50));
		this.counter = 0;
	}

	private void computeNextMovement() {
		if (isAtSimilarWidth()) {
			if (isOtherPlayerOverMe()) {
				this.nextMovementDuration = generateRandom(TimeUnit.MILLISECONDS.toMillis(100));
				runAway();
			} else {
				runTowardsOtherPlayer();
				if (attackIsPossible())
					moveUp();
			}
		} else {
			if (shouldIBreakOutOfSurrounding()) {
				breakOutOfSurrounding();
			} else {
				chaseOtherPlayer();
				if (isOtherPlayerOverMe()) {
					moveUp();
				} else {
					moveDown();
				}
			}
		}
		jumpIfStuck();
	}

	private void breakOutOfSurrounding() {
		if (this.player.getCenterX() < ModelConstants.STANDARD_WORLD_SIZE * 0.5) {
			doMoveRight();
		} else {
			doMoveLeft();
		}
	}

	private boolean attackIsPossible() {
		return isAtSimilarWidth() && isAtSimilarHeight();
	}

	private boolean isAtSimilarHeight() {
		return Math.abs(this.player.getCenterY() - this.otherPlayer.getCenterY()) < ModelConstants.STANDARD_WORLD_SIZE * 0.15;
	}

	private int generateRandom(long milliseconds) {
		double randomNumber = (this.random.nextGaussian() + Math.PI / 2) / Math.PI;
		return (int) (randomNumber * milliseconds);
	}

	public void jumpIfStuck() {
		if (stuckAgainstWall()) {
			moveUp();
		}
	}

	private boolean stuckAgainstWall() {
		return player.movementX() == 0;
	}

	private boolean shouldIBreakOutOfSurrounding() {
		boolean otherPlayerIsOverMe = isOtherPlayerOverMe();
		if (otherPlayerIsOverMe) {
			if (atLeftBorderAndOtherPlayerClose() || atRightBorderAndOtherPlayerClose()) {
				return true;
			}
		}
		return false;
	}

	private void runAway() {
		if (shouldIBreakOutOfSurrounding()) {
			breakOutOfSurrounding();
		} else {
			if (isLeftToOtherPlayer()) {
				doMoveLeft();
			} else {
				doMoveRight();
			}
		}
	}

	private void doMoveLeft() {
		this.rememberMoveLeft = true;
		this.rememberMoveRight = false;
	}

	private void doMoveRight() {
		this.rememberMoveLeft = false;
		this.rememberMoveRight = true;
	}

	private void moveUp() {
		this.rememberMoveUp = true;
	}

	private void moveDown() {
		this.rememberMoveUp = false;
	}

	private void runTowardsOtherPlayer() {
		if (isLeftToOtherPlayer()) {
			doMoveRight();
		} else {
			doMoveLeft();
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
		moveRememberedHorizontal();
		moveRememberedVertical();
	}

	private void moveRememberedVertical() {
		if (this.rememberMoveUp)
			this.playerMovement.tryMoveUp();
		else
			playerMovement.tryMoveDown();
	}

	private void moveRememberedHorizontal() {
		playerMovement.removeHorizontalMovement();
		if (this.rememberMoveLeft)
			this.playerMovement.tryMoveLeft();
		if (this.rememberMoveRight)
			this.playerMovement.tryMoveRight();
	}

	private boolean isAtSimilarWidth() {
		return Math.abs(this.player.getCenterX() - this.otherPlayer.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.15;
	}

	private boolean isLeftToOtherPlayer() {
		return this.otherPlayer.getCenterX() > this.player.getCenterX();
	}

	private boolean atRightBorderAndOtherPlayerClose() {
		return this.player.getCenterX() > ModelConstants.STANDARD_WORLD_SIZE * 0.85
				&& Math.abs(this.player.getCenterX() - this.otherPlayer.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.1;
	}

	private boolean atLeftBorderAndOtherPlayerClose() {
		return this.player.getCenterX() < ModelConstants.STANDARD_WORLD_SIZE * 0.15
				&& Math.abs(this.otherPlayer.getCenterX() - this.player.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.1;
	}

	private boolean isOtherPlayerOverMe() {
		return this.otherPlayer.minY() > this.player.maxY();
	}

}
