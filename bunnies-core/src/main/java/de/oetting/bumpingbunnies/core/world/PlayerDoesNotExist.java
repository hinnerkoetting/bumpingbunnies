package de.oetting.bumpingbunnies.core.world;

public class PlayerDoesNotExist extends RuntimeException {

	public PlayerDoesNotExist(int playerId) {
		super("Id not found: " + playerId);
	}

}