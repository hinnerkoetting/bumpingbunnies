package de.jumpnbump.usecases.game.communication.objects;

import de.jumpnbump.usecases.game.model.PlayerState;

public class JsonWrapper {

	private final int id;
	private final PlayerState playerState;

	public PlayerState getPlayerState() {
		return this.playerState;
	}

	public int getId() {
		return this.id;
	}

	public JsonWrapper(int id, PlayerState playerState) {
		this.id = id;
		this.playerState = playerState;
	}
}
