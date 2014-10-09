package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

public class PlayerIsDeadMessage {

	private final int idOfDeadPlayer;

	public PlayerIsDeadMessage(int idOfDeadPlayer) {
		this.idOfDeadPlayer = idOfDeadPlayer;
	}

	public int getPlayerId() {
		return this.idOfDeadPlayer;
	}

}
