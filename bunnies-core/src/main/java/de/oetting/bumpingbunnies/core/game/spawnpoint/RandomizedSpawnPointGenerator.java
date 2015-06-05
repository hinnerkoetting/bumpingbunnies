package de.oetting.bumpingbunnies.core.game.spawnpoint;

import java.util.List;
import java.util.Random;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class RandomizedSpawnPointGenerator implements SpawnPointGenerator {

	private final List<SpawnPoint> allSpawnPoints;
	private final Random random;

	public RandomizedSpawnPointGenerator(List<SpawnPoint> allSpawnPoints) {
		this.allSpawnPoints = allSpawnPoints;
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public SpawnPoint nextSpawnPoint() {
		int index = chooseRandomValue();
		return this.allSpawnPoints.get(index);
	}

	private int chooseRandomValue() {
		return this.random.nextInt(allSpawnPoints.size());
	}

}
