package de.jumpnbump.usecases.game.factories;

import android.content.Context;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldFactory {

	public static World create(Configuration configuration, Context context) {
		WorldObjectsBuilder factory = configuration.getWorldConfiguration()
				.createInputconfigurationClass();
		World world = new World(factory, context);
		world.buildWorld(configuration.getNumberPlayer());
		return world;
	}
}
