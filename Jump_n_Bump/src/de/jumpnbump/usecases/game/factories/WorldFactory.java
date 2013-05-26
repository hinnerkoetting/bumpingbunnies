package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.model.WorldObjectsFactory;

public class WorldFactory {

	public static World create() {
		WorldObjectsFactory factory = new WorldObjectsFactory();
		World world = new World(factory);
		world.buildWorld();
		return world;
	}
}
