package de.jumpnbump.usecases.game;

import de.jumpnbump.usecases.game.businesslogic.PlayerMovement;
import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;

public class WorldController {
	private World world;
	private TouchService movementService;
	private PlayerMovement playerMovement;

	public WorldController(World world, TouchService movementService,
			PlayerMovement playerMovement) {
		this.world = world;
		this.movementService = movementService;
		this.playerMovement = playerMovement;
	}

	public void nextStep() {
		this.movementService.executeUserInput();
		this.playerMovement.nextStep();
	}

}
