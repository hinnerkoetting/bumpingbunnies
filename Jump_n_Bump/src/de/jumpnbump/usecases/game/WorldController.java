package de.jumpnbump.usecases.game;

import de.jumpnbump.usecases.game.businesslogic.TouchService;
import de.jumpnbump.usecases.game.model.World;

public class WorldController {

	private World world;
	private TouchService movementService;

	public WorldController(World world, TouchService movementService) {
		this.world = world;
		this.movementService = movementService;
	}

	public void nextStep() {
		this.movementService.executeUserInput();
	}

}
