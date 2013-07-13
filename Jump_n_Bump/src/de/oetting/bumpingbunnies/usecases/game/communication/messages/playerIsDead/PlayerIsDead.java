package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class PlayerIsDead {

	private final int idOfDeadPlayer;
	private final SpawnPoint nextSpawnPoint;

	public PlayerIsDead(int idOfDeadPlayer, SpawnPoint nextSpawnPoint) {
		super();
		this.idOfDeadPlayer = idOfDeadPlayer;
		this.nextSpawnPoint = nextSpawnPoint;
	}

	public int getIdOfDeadPlayer() {
		return this.idOfDeadPlayer;
	}

	public SpawnPoint getNextSpawnPoint() {
		return this.nextSpawnPoint;
	}

}
