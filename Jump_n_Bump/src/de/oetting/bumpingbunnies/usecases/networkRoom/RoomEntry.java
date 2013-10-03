package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class RoomEntry {
	private final PlayerProperties playerProperties;
	private final MySocket socket;
	private final int socketIndex;

	public RoomEntry(PlayerProperties playerProperties, MySocket socket,
			int socketIndex) {
		super();
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
		return this.playerProperties.getPlayerId() + " "
				+ this.playerProperties.getPlayerName();
	}

	public Opponent createOponent() {
		return this.socket.getOwner();
	}
}
