package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class WorldController {
	private List<InputService> inputServices;
	private List<PlayerMovementController> playermovements;
	private List<StateSender> stateSender;
	private SpawnPointGenerator spawnPointGenerator;

	public WorldController(List<PlayerMovementController> playermovements,
			List<InputService> movementServices, List<StateSender> stateSender,
			SpawnPointGenerator spawnPointGenerator) {
		this.playermovements = playermovements;
		this.inputServices = movementServices;
		this.stateSender = stateSender;
		this.spawnPointGenerator = spawnPointGenerator;
	}

	public void addMovementService(InputService movementService) {
		this.inputServices.add(movementService);
	}

	public void nextStep(long delta) {
		for (InputService movementService : this.inputServices) {
			movementService.executeUserInput();
		}
		for (PlayerMovementController movement : this.playermovements) {
			movement.nextStep(delta);
			checkForJumpedPlayers();
		}
		for (StateSender ss : this.stateSender) {
			ss.sendPlayerCoordinates();
		}
		killPlayersOutOfPlayZone();
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

	private void resetCoordinate(Player playerUnder) {
		SpawnPoint spawnPoint = this.spawnPointGenerator.nextSpawnPoint();
		playerUnder.setCenterX(spawnPoint.getX());
		playerUnder.setCenterY(spawnPoint.getY());
		playerUnder.setMovementY(0);
		playerUnder.setMovementX(0);
	}

	private void increaseScore(Player playerTop) {
		PlayerState state = playerTop.getState();
		state.setScore(state.getScore() + 1);
	}

	public void switchInputServices(List<InputService> createInputServices) {
		this.inputServices = createInputServices;
	}

	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>(
				this.playermovements.size());
		for (PlayerMovementController movement : this.playermovements) {
			players.add(movement.getPlayer());
		}
		return players;
	}

	public void applyPlayers(List<Player> storedPlayers) {
		for (PlayerMovementController movement : this.playermovements) {
			for (Player storedPlayer : storedPlayers) {
				if (movement.getPlayer().id() == storedPlayer.id()) {
					PlayerState newState = movement.getPlayer().getState();
					PlayerState oldState = storedPlayer.getState();
					oldState.copyContentTo(newState);
				}
			}
		}

	}
}
