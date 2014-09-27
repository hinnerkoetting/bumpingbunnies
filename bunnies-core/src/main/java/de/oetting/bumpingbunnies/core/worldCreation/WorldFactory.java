package de.oetting.bumpingbunnies.core.worldCreation;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.worldCreation.XmlWorldBuilderState;

public class WorldFactory {

	public World create(XmlWorldBuilderState state) {
		World world = new World();
		world.replaceAllWalls(state.getAllWalls());
		world.replaceAllIcyWalls(state.getAllIcyWalls());
		world.replaceAllJumpers(state.getAllJumper());
		world.replaceAllWaters(state.getWaters());
		world.replaceAllSpawnPoints(state.getSpawnPoints());
		world.addToAllObjects();
		return world;
	}
}