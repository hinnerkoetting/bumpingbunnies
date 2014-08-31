package de.oetting.bumpingbunnies.usecases.game.configuration;


public class PlayerProperties {

	private final int playerId;
	private final String playerName;

	public PlayerProperties(int playerId, String playerName) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public String getPlayerName() {
		return this.playerName;
	}

}
