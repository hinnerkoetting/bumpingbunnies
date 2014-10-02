package de.oetting.bumpingbunnies.model.configuration;

public class GameStartParameter {

	public static final int SINGPLE_PLAYER_ID = 0;

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
