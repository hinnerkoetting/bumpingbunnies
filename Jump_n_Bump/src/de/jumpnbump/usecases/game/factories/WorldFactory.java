package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.model.worldfactory.ClassicJumpnBumpWorldBuilder;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldFactory {

	public static World create() {
		WorldObjectsBuilder factory = new ClassicJumpnBumpWorldBuilder();
		World world = new World(factory);
		world.buildWorld();
		return world;
	}
}
