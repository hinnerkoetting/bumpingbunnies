package de.oetting.bumpingbunnies.worldcreator.load.gameObjects;

import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;

public class WorldFactory {

	public World create(XmlWorldBuilderState state) {
		World world = new World(new WorldProperties());
		world.replaceAllWalls(state.getAllWalls());
		world.replaceAllIcyWalls(state.getAllIcyWalls());
		world.replaceAllJumpers(state.getAllJumper());
		world.replaceAllWaters(state.getWaters());
		world.replaceAllSpawnPoints(state.getSpawnPoints());
		world.replaceBackgrounds(state.getBackground());
		world.addToAllObjects();
		world.init();
		return world;
	}
}
