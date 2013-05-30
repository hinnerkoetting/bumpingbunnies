package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.configuration.WorldConfiguration;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldFactory {

	public static World create(WorldConfiguration configuration) {
		WorldObjectsBuilder factory = configuration
				.createInputconfigurationClass();
		World world = new World(factory);
		world.buildWorld();
		return world;
	}
}
