package de.oetting.bumpingbunnies.core.network.room;

import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class RoomEntry {

	private final PlayerProperties playerProperties;
	private final ConnectionIdentifier opponent;

	public RoomEntry(PlayerProperties playerProperties, ConnectionIdentifier opponent) {
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

	public ConnectionIdentifier getOponent() {
		return this.opponent;
	}

	public String getPlayerName() {
		return playerProperties.getPlayerName();
	}

	public int getPlayerId() {
		return playerProperties.getPlayerId();
	}
}
