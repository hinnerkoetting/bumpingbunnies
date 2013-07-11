package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead;

public class PlayerIsDead {

	private final int idOfDeadPlayer;

	public PlayerIsDead(int idOfDeadPlayer) {
		super();
		this.idOfDeadPlayer = idOfDeadPlayer;
	}

	public int getIdOfDeadPlayer() {
		return this.idOfDeadPlayer;
	}

}
