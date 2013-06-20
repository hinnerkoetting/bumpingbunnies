package de.jumpnbump.usecases.game.businesslogic;

import java.util.List;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;
import de.jumpnbump.usecases.game.model.SpawnPoint;

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
	}

	private void increaseScore(Player playerTop) {
		PlayerState state = playerTop.getState();
		state.setScore(state.getScore() + 1);
	}

	public void destroy() {
		for (InputService is : this.inputServices) {
			is.destroy();
		}
	}

	public void switchInputServices(List<InputService> createInputServices) {
		for (InputService is : this.inputServices) {
			is.destroy();
		}
		this.inputServices = createInputServices;
	}
}
