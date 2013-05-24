package de.jumpnbump.usecases.game;

import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.services.MovementService;

public class WorldController {

	private World world;
	private MovementService movementService;

	public WorldController(World world, MovementService movementService) {
		this.world = world;
		this.movementService = movementService;
	}

	public void nextStep() {
		this.movementService.executeUserInput();
	}

}
