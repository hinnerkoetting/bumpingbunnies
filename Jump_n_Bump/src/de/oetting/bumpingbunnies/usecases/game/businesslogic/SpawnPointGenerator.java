package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public interface SpawnPointGenerator {
	SpawnPoint nextSpawnPoint();
}
