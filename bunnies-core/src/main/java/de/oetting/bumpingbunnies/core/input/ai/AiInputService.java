package de.oetting.bumpingbunnies.core.input.ai;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AiInputService implements OpponentInput {

	private static Logger LOGGER = LoggerFactory.getLogger(AiInputService.class);

	private final Bunny aiPlayer;
	private final Random randomGenerator;
	private final World world;
	
	private boolean rememberMoveLeft;
	private boolean rememberMoveRight;
	private boolean rememberMoveUp;
	private int durationOfCurrentMovementPhase = 0;
	private long nextMovementDurationPhase = 0;
	private Bunny closestEnemyPlayer;


	public AiInputService(Bunny playerMovement, World world) {
		this.world = world;
		this.aiPlayer = playerMovement;
		this.randomGenerator = new Random(System.currentTimeMillis());
		LOGGER.info("initialising ai for player %d", this.aiPlayer.id());
	}

	@Override
	public void executeNextStep(long deltaSinceLastLast) {
		this.closestEnemyPlayer = findNearestOtherPlayer();
		this.durationOfCurrentMovementPhase += deltaSinceLastLast;
		if (this.durationOfCurrentMovementPhase > this.nextMovementDurationPhase) {
			reset();
			computeNextMovement();
		}
		moveRememberedMovement();
	}

	private Bunny findNearestOtherPlayer() {
		double nearest = Double.MAX_VALUE;
		Bunny nearestPlayer = null;
		for (Bunny p : this.world.getAllConnectedBunnies()) {
			if (p.id() != this.aiPlayer.id()) {
				double distance = distance(p, this.aiPlayer);
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
		return this.aiPlayer;
	}

	private double distance(Bunny p1, Bunny p2) {
		return Math.pow(p1.centerX() - p2.centerX(), 2) + Math.pow(p1.centerY() - p2.centerY(), 2);
	}

	private void reset() {
		this.rememberMoveRight = false;
		this.rememberMoveLeft = false;
		this.rememberMoveUp = false;
		this.nextMovementDurationPhase = generateRandom(TimeUnit.MILLISECONDS.toMillis(50));
		this.durationOfCurrentMovementPhase = 0;
	}

	private void computeNextMovement() {
		if (isAtSimilarWidth()) {
			if (isOtherPlayerOverMe()) {
				this.nextMovementDurationPhase = generateRandom(TimeUnit.MILLISECONDS.toMillis(100));
				runAway();
			} else {
				runTowardsOtherPlayer();
				if (attackIsPossible() && !isInWater())
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

	private boolean isInWater() {
		return aiPlayer.isInWater();
	}

	private void breakOutOfSurrounding() {
		if (this.aiPlayer.getCenterX() < ModelConstants.STANDARD_WORLD_SIZE * 0.5) {
			doMoveRight();
		} else {
			doMoveLeft();
		}
	}

	private boolean attackIsPossible() {
		return isAtSimilarWidth() && isAtSimilarHeight();
	}

	private boolean isAtSimilarHeight() {
		return Math.abs(this.aiPlayer.getCenterY() - this.closestEnemyPlayer.getCenterY()) < ModelConstants.STANDARD_WORLD_SIZE * 0.15;
	}

	private int generateRandom(long milliseconds) {
		double randomNumber = (this.randomGenerator.nextGaussian() + Math.PI / 2) / Math.PI;
		return (int) (randomNumber * milliseconds);
	}

	public void jumpIfStuck() {
		if (stuckAgainstWall()) {
			moveUp();
		}
	}

	private boolean stuckAgainstWall() {
		return aiPlayer.movementX() == 0;
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
			this.aiPlayer.setJumping(true);
		else
			this.aiPlayer.setJumping(false);
	}

	private void moveRememberedHorizontal() {
		aiPlayer.setNotMoving();
		if (this.rememberMoveLeft)
			this.aiPlayer.setMovingLeft();
		if (this.rememberMoveRight)
			aiPlayer.setMovingRight();
	}

	private boolean isAtSimilarWidth() {
		return Math.abs(this.aiPlayer.getCenterX() - this.closestEnemyPlayer.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.15;
	}

	private boolean isLeftToOtherPlayer() {
		return this.closestEnemyPlayer.getCenterX() > this.aiPlayer.getCenterX();
	}

	private boolean atRightBorderAndOtherPlayerClose() {
		return this.aiPlayer.getCenterX() > ModelConstants.STANDARD_WORLD_SIZE * 0.85
				&& Math.abs(this.aiPlayer.getCenterX() - this.closestEnemyPlayer.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.1;
	}

	private boolean atLeftBorderAndOtherPlayerClose() {
		return this.aiPlayer.getCenterX() < ModelConstants.STANDARD_WORLD_SIZE * 0.15
				&& Math.abs(this.closestEnemyPlayer.getCenterX() - this.aiPlayer.getCenterX()) < ModelConstants.STANDARD_WORLD_SIZE * 0.1;
	}

	private boolean isOtherPlayerOverMe() {
		return this.closestEnemyPlayer.minY() > this.aiPlayer.minY();
	}

}
