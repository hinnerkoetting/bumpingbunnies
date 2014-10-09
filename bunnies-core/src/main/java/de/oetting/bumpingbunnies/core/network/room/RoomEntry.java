package de.oetting.bumpingbunnies.core.network.room;

import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class RoomEntry {

	private final PlayerProperties playerProperties;
	private final Opponent opponent;

	public RoomEntry(PlayerProperties playerProperties, Opponent opponent) {
		this.playerProperties = playerProperties;
		this.opponent = opponent;
	}

	public PlayerProperties getPlayerConfiguration() {
		return this.playerProperties;
	}

	public PlayerProperties getPlayerProperties() {
		return this.playerProperties;
	}

	@Override
	public String toString() {
		return this.playerProperties.getPlayerId() + " " + this.playerProperties.getPlayerName();
	}

	public Opponent createOponent() {
		return this.opponent;
	}

	public String getPlayerName() {
		return playerProperties.getPlayerName();
	}

	public int getPlayerId() {
		return playerProperties.getPlayerId();
	}
}
