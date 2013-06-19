package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.jumpnbump.usecases.game.model.FixedWorldObject;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.SpawnPoint;

public interface WorldObjectsBuilder {

	List<Player> createAllPlayers(int number);

	Collection<FixedWorldObject> createAllWalls(Context context);

	List<SpawnPoint> createSpawnPoints();

}