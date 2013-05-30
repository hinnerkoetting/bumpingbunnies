package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.model.worldfactory.OneWallObjectsBuilder;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldFactory {

	public static World create() {
		WorldObjectsBuilder factory = new OneWallObjectsBuilder();
		World world = new World(factory);
		world.buildWorld();
		return world;
	}
}
