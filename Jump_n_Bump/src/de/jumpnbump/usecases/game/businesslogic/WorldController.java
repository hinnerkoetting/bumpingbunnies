package de.jumpnbump.usecases.game.businesslogic;

import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.BloodParticle;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;
import de.jumpnbump.usecases.game.model.SpawnPoint;

public class WorldController {
	private List<InputService> inputServices;
	private List<PlayerMovementController> playermovements;
	private List<StateSender> stateSender;
	private SpawnPointGenerator spawnPointGenerator;

	private List<BloodParticle> newBloodParticles;

	public WorldController(List<PlayerMovementController> playermovements,
			List<InputService> movementServices, List<StateSender> stateSender,
			SpawnPointGenerator spawnPointGenerator) {
		this.playermovements = playermovements;
		this.inputServices = movementServices;
		this.stateSender = stateSender;
		this.spawnPointGenerator = spawnPointGenerator;
		this.newBloodParticles = new LinkedList<BloodParticle>();
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
		createBlood(playerUnder);
	}

	private void createBlood(Player playerUnder) {
		BloodParticle blood = new BloodParticle(playerUnder.centerX(),
				playerUnder.centerY(), 100, 1000);
		this.newBloodParticles.add(blood);
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

	public List<BloodParticle> getNewBloodParticles() {
		return this.newBloodParticles;
	}
}
