package de.oetting.bumpingbunnies.model.game.world;

public class PlayerDoesNotExist extends RuntimeException {

	public PlayerDoesNotExist(int playerId) {
		super("Id not found: " + playerId);
	}

}