package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public interface WorldObjectsBuilder {

	Collection<GameObject> createAllWalls(Context context);

	List<SpawnPoint> createSpawnPoints();

}