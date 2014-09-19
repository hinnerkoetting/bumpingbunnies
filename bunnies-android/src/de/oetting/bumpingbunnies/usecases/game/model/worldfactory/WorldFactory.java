package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import android.content.Context;
import de.oetting.bumpingbunnies.core.world.World;

public class WorldFactory {

	public World create(WorldObjectsParser parser, Context context) {
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
