package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.util.Collection;
import java.util.List;

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public interface WorldObjectsParser {

	World build(ResourceProvider provider, XmlReader xmlReader);

	Collection<Wall> getAllWalls();

	Collection<IcyWall> getAllIcyWalls();

	Collection<Jumper> getAllJumpers();

	Collection<Water> getAllWaters();

	List<SpawnPoint> getAllSpawnPoints();

	/**
	 * 
	 * Bezieht sich zu stark auf Android
	 */
	@Deprecated
	int getResourceId();

}