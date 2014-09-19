package de.oetting.bumpingbunnies.core.game.spawnpoint;

import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public interface SpawnPointGenerator {
	SpawnPoint nextSpawnPoint();
}
