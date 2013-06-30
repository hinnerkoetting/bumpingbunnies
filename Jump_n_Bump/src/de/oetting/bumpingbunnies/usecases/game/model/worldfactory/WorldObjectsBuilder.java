package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public interface WorldObjectsBuilder {

	List<Player> createAllPlayers(int number);

	Collection<FixedWorldObject> createAllWalls(Context context);

	List<SpawnPoint> createSpawnPoints();

}