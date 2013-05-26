package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.World;

public class WorldFactory {

	public static World create() {
		World world = new World();
		return world;
	}
}
