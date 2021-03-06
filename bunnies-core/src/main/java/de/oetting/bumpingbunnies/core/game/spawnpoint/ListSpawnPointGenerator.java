package de.oetting.bumpingbunnies.core.game.spawnpoint;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class ListSpawnPointGenerator implements SpawnPointGenerator {

	private int currentIndex;
	private List<SpawnPoint> allSpawnPoints;

	public ListSpawnPointGenerator(List<SpawnPoint> allSpawnPoints) {
		this.allSpawnPoints = allSpawnPoints;
	}

	@Override
	public SpawnPoint nextSpawnPoint() {
		int index = getNextIndex();
		return this.allSpawnPoints.get(index);
	}

	private int getNextIndex() {
		this.currentIndex++;
		if (this.currentIndex >= this.allSpawnPoints.size()) {
			this.currentIndex = 0;
		}
		return this.currentIndex;
	}
}
