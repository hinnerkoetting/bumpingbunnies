package de.oetting.bumpingbunnies.core.game.spawnpoint;

import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class ResetToScorePoint {

	public static void resetPlayerToSpawnPoint(SpawnPointGenerator spawnPointGenerator, Player playerToReset) {
		SpawnPoint spawnPoint = spawnPointGenerator.nextSpawnPoint();
		resetPlayerToSpawnPoint(spawnPoint, playerToReset);
	}

	public static void resetPlayerToSpawnPoint(SpawnPoint spawnPoint, Player playerToReset) {
		playerToReset.setCenterX(spawnPoint.getX());
		playerToReset.setCenterY(spawnPoint.getY());
		playerToReset.setMovementY(0);
		playerToReset.setMovementX(0);
	}
}
