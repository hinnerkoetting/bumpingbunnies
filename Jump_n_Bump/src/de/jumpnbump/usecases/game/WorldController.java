package de.jumpnbump.usecases.game;

import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;

public class WorldController {
	private World world;
	private TouchService movementService;
	private PlayerMovement playerMovement;
	private final PlayerMovement player2Movement;

	public WorldController(World world, TouchService movementService,
			PlayerMovement playerMovement, PlayerMovement player2Movement) {
		this.world = world;
		this.movementService = movementService;
		this.playerMovement = playerMovement;
		this.player2Movement = player2Movement;
	}

	public void nextStep(long delta) {
		this.movementService.executeUserInput();
		this.playerMovement.nextStep(delta);
		this.player2Movement.nextStep(delta);
	}

}
