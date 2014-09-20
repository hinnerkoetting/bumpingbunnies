package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;

public class WorldFactory {

	public World create(WorldObjectsParser parser) {
		World world = new World();
		world.replaceAllWalls(parser.getAllWalls());
		world.replaceAllIcyWalls(parser.getAllIcyWalls());
		world.replaceAllJumpers(parser.getAllJumpers());
		world.replaceAllWaters(parser.getAllWaters());
		world.replaceAllSpawnPoints(parser.getAllSpawnPoints());
		world.addToAllObjects();
		return world;
	}
}
