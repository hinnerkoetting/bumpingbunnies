package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

public class PlayerIsDead {

	private final int idOfDeadPlayer;

	public PlayerIsDead(int idOfDeadPlayer) {
		this.idOfDeadPlayer = idOfDeadPlayer;
	}

	public int getIdOfDeadPlayer() {
		return this.idOfDeadPlayer;
	}

}
