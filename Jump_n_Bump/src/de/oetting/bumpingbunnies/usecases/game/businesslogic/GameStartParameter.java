package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

public class GameStartParameter {

	private final Configuration configuration;
	private final int playerId;

	public GameStartParameter(Configuration configuration, int playerId) {
		this.configuration = configuration;
		this.playerId = playerId;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public int getPlayerId() {
		return this.playerId;
	}

}
