package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class RoomEntry {
	private final PlayerProperties playerProperties;
	private final MySocket socket;
	private final int socketIndex;

	public RoomEntry(PlayerProperties playerProperties, MySocket socket, int socketIndex) {
		this.playerProperties = playerProperties;
		this.socket = socket;
		this.socketIndex = socketIndex;
	}

	public PlayerProperties getPlayerConfiguration() {
		return this.playerProperties;
	}

	public PlayerProperties getPlayerProperties() {
		return this.playerProperties;
	}

	public MySocket getSocket() {
		return this.socket;
	}

	public int getSocketIndex() {
		return this.socketIndex;
	}

	@Override
	public String toString() {
		return this.playerProperties.getPlayerId() + " " + this.playerProperties.getPlayerName();
	}

	public Opponent createOponent() {
		return this.socket.getOwner();
	}

	public String getPlayerName() {
		return playerProperties.getPlayerName();
	}

	public int getPlayerId() {
		return playerProperties.getPlayerId();
	}
}