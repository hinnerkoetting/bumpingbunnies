package de.jumpnbump.usecases.game.android.input.ai;

import java.util.List;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.AbstractControlledMovement;
import de.jumpnbump.usecases.game.android.input.PathFinder.PathFinder;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;
import de.jumpnbump.usecases.game.model.World;

/**
 * Should run through all walls one step at a time. Used to optimized pathfinder
 * 
 */
public class RunnerAiInputService extends AbstractControlledMovement {

	private static final MyLog LOGGER = Logger
			.getLogger(RunnerAiInputService.class);
	private final World world;
	private final RunnerAiInputState state;
	private final PathFinder pathFinder;

	public RunnerAiInputService(World world,
			PlayerMovementController playerMovement, PathFinder pathFinder) {
		super(playerMovement);
		this.world = world;
		this.pathFinder = pathFinder;
		this.state = new RunnerAiInputState();
	}

	@Override
	public void executeUserInput() {
		decideAboutNextMovement();
		executeRememberedMovement();
	}

	protected void decideAboutNextMovement() {
		if (!this.state.hasTarget()) {
			LOGGER.info("finding new target");

			Wall nextTarget = findNextTarget();
			LOGGER.info("Next Target %s ", nextTarget.toString());
			this.state.setNextTarget(nextTarget, getMovedPlayer());
		}
		chaseNextTarget();
		checkTargetReached();
	}

	private void checkTargetReached() {
		if (touchesPlayerThisHorizontalPosition(this.state.nextTargetCenterX())) {
			Player player = getMovedPlayer();
			if (Math.abs(this.state.nextTargetCenterY() - player.maxY()) < 0.1) {
				this.state.removeNextTarget();
				LOGGER.info("Removing next target");
			}
		}
	}

	private Wall findNextTarget() {
		return findLowestTargetAboveMe();
	}

	private Wall findLowestTargetAboveMe() {
		LOGGER.info("Playerposition is %s", getMovedPlayer().getState()
				.toString());
		List<Wall> allWalls = this.world.getAllWalls();
		Wall lowestWall = null;
		for (Wall w : allWalls) {
			if (w.minY() > getMovedPlayer().minY()) {
				if (lowestWall != null) {
					if (w.minY() < lowestWall.minY()) {
						lowestWall = w;
					}
				} else {
					lowestWall = w;
				}
			}

		}
		if (lowestWall == null) {
			LOGGER.info("At top wall. running towards lowest wall");
			return findLowestTarget();
		} else {
			return lowestWall;

		}
	}

	private Wall findLowestTarget() {
		List<Wall> allWalls = this.world.getAllWalls();
		Wall lowestWall = allWalls.get(0);
		for (Wall w : allWalls) {
			if (w.minY() < lowestWall.minY()) {
				lowestWall = w;
			}
		}
		return lowestWall;
	}

	private void chaseNextTarget() {
		Player player = this.getMovedPlayer();
		if (this.state.nextTargetCenterX() < player.getCenterX()) {
			rememberMoveLeft();
		} else if (this.state.nextTargetCenterX() > player.getCenterX()) {
			rememberMoveRight();
		}
		if (isCurrentlyJumping()) {
			if (isStandingOnGround()) {
				conditionallyJump();
			} else {
				moveUp();
			}
		} else {
			conditionallyJump();
		}

	}

	private boolean isStandingOnGround() {
		return getPlayerMovement().isStandingOnGround();

	}

	private boolean isCurrentlyJumping() {
		return getPlayerMovement().isJumping();
	}

	private void conditionallyJump() {
		if (this.pathFinder.canBeReachedByJumping(
				this.state.nextTargetCenterX(), this.state.nextTargetCenterY())) {
			moveUp();
		} else {
			moveDown();
		}

	}

	@Override
	public void destroy() {

	}

}
