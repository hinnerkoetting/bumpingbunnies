package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction {

	private final List<PlayerMovementController> playermovements;
	private final SpawnPointGenerator spawnPointGenerator;

	public BunnyMovementStep(List<PlayerMovementController> playermovements, SpawnPointGenerator spawnPointGenerator) {
		super();
		this.spawnPointGenerator = spawnPointGenerator;
		this.playermovements = Collections.unmodifiableList(playermovements);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementController movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
			checkForJumpedPlayers();
		}
		killPlayersOutOfPlayZone();
	}

	private void checkForJumpedPlayers() {
		for (PlayerMovementController movement : this.playermovements) {
			Player playerUnder = movement.isOnTopOfOtherPlayer();
			if (playerUnder != null) {
				handleJumpedPlayer(playerUnder, movement.getPlayer());
			}
		}
	}

	private void handleJumpedPlayer(Player playerUnder, Player playerTop) {
		increaseScore(playerTop);
		resetCoordinate(playerUnder);
	}

	private void increaseScore(Player playerTop) {
		PlayerState state = playerTop.getState();
		state.setScore(state.getScore() + 1);
	}

	private void killPlayersOutOfPlayZone() {
		for (PlayerMovementController movement : this.playermovements) {
			Player player = movement.getPlayer();
			if (player.getCenterY() < -ModelConstants.MAX_VALUE * 0.1) {
				PlayerState state = player.getState();
				state.setScore(state.getScore() - 1);
				resetCoordinate(player);
			}
		}

	}

	private void resetCoordinate(Player playerUnder) {
		SpawnPoint spawnPoint = this.spawnPointGenerator.nextSpawnPoint();
		playerUnder.setCenterX(spawnPoint.getX());
		playerUnder.setCenterY(spawnPoint.getY());
		playerUnder.setMovementY(0);
		playerUnder.setMovementX(0);
	}
}
