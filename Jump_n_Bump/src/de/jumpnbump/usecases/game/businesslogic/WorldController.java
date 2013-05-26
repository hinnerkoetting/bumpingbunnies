package de.jumpnbump.usecases.game.businesslogic;

import java.util.List;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class WorldController {
	private List<InputService> inputServices;
	private List<GamePlayerController> playermovements;

	public WorldController(List<GamePlayerController> playermovements,
			List<InputService> movementServices) {
		this.playermovements = playermovements;
		this.inputServices = movementServices;
	}

	public void addMovementService(InputService movementService) {
		this.inputServices.add(movementService);
	}

	public void nextStep(long delta) {
		for (InputService movementService : this.inputServices) {
			movementService.executeUserInput();
		}
		for (GamePlayerController movement : this.playermovements) {
			movement.nextStep(delta);
		}
		checkForJumpedPlayers();
	}

	private void checkForJumpedPlayers() {
		for (GamePlayerController movement : this.playermovements) {
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
