package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public interface WorldObjectsBuilder {

	Collection<GameObjectWithImage> createAllWalls(Context context);

	List<SpawnPoint> createSpawnPoints();

}