package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public interface WorldObjectsBuilder {

	World build(Context context);

	Collection<Wall> getAllWalls();

	Collection<IcyWall> getAllIcyWalls();

	Collection<Jumper> getAllJumpers();

	Collection<Water> getAllWaters();

	List<SpawnPoint> createSpawnPoints();

}