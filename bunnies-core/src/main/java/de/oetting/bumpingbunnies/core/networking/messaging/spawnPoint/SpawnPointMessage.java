package de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class SpawnPointMessage {

	private final SpawnPoint spawnPoint;
	private final int playerId;

	public SpawnPointMessage(SpawnPoint spawnPoint, int playerId) {
		this.spawnPoint = spawnPoint;
		this.playerId = playerId;
	}

	public SpawnPoint getSpawnPoint() {
		return this.spawnPoint;
	}

	public int getPlayerId() {
		return this.playerId;
	}

}
