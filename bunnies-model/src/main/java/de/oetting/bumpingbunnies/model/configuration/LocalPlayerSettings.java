package de.oetting.bumpingbunnies.model.configuration;


/**
 * Properties which define the local player and should be transferred to other
 * players.
 * 
 */
public class LocalPlayerSettings {

	private final String playerName;

	public LocalPlayerSettings(String playerName) {
		super();
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return this.playerName;
	}

}
