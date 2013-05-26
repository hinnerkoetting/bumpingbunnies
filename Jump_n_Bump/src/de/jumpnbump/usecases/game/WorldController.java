package de.jumpnbump.usecases.game;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.businesslogic.MovementService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;

public class WorldController {
	private List<MovementService> movementServices;
	private PlayerMovement playerMovement;
	private final PlayerMovement player2Movement;

	public WorldController(PlayerMovement playerMovement,
			PlayerMovement player2Movement) {
		this.playerMovement = playerMovement;
		this.player2Movement = player2Movement;
		this.movementServices = new ArrayList<MovementService>();
	}

	public void addMovementService(MovementService movementService) {
		this.movementServices.add(movementService);
	}

	public void nextStep(long delta) {
		for (MovementService movementService : this.movementServices) {
			movementService.executeUserInput();
		}
		this.playerMovement.nextStep(delta);
		this.player2Movement.nextStep(delta);
	}

}
