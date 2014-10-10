package de.oetting.bumpingbunnies.core.game.spawnpoint;

import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class ResetToScorePoint {

	public static void resetPlayerToSpawnPoint(SpawnPoint spawnPoint, Player playerToReset) {
		playerToReset.setCenterX(spawnPoint.getX());
		playerToReset.setCenterY(spawnPoint.getY());
		playerToReset.setMovementY(0);
		playerToReset.setMovementX(0);
	}
}
