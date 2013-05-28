package de.jumpnbump.usecases.game.businesslogic;

import java.util.List;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class WorldController {
	private List<InputService> inputServices;
	private List<PlayerMovementController> playermovements;
	private List<StateSender> stateSender;

	public WorldController(List<PlayerMovementController> playermovements,
			List<InputService> movementServices, List<StateSender> stateSender) {
		this.playermovements = playermovements;
		this.inputServices = movementServices;
		this.stateSender = stateSender;
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
		}
		for (StateSender ss : this.stateSender) {
			ss.sendPlayerCoordinates();
		}
		checkForJumpedPlayers();
	}

	private void checkForJumpedPlayers() {
		for (PlayerMovementController movement : this.playermovements) {
			Player playerUnder = movement.isOnTopOfOtherPlayer();
			if (playerUnder != null) {
				Player playerOnTop = movement.getPlayer();
				PlayerState state = playerOnTop.getState();
				state.setScore(state.getScore() + 1);
				resetPosition(playerUnder, playerOnTop);
			}
		}
	}

	private void resetPosition(Player playerUnder, Player playerOver) {
		PlayerState state = playerUnder.getState();
		if (state.getCenterX() > 0.75) {
			state.setCenterX(0.1);
		} else {
			state.setCenterX(state.getCenterX() + 0.2);
		}
		state.setCenterY(0.5);

		PlayerState state2 = playerOver.getState();
		state2.setMovementY(-0.001);
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
