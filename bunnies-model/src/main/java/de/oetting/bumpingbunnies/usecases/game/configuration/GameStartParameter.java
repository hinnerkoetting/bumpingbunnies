package de.oetting.bumpingbunnies.usecases.game.configuration;


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
