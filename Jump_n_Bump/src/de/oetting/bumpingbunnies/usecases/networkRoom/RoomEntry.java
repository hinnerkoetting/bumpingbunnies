package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;

public class RoomEntry {
	// TODO: add yourself
	private final OtherPlayerConfiguration playerConfiguration;

	public RoomEntry(OtherPlayerConfiguration playerConfiguration) {
		super();
		this.playerConfiguration = playerConfiguration;
	}

	public OtherPlayerConfiguration getPlayerConfiguration() {
		return this.playerConfiguration;
	}

	@Override
	public String toString() {
		if (this.playerConfiguration == null) {
			return "You";
		} else {
			return Integer.toString(this.playerConfiguration.getPlayerId());
		}
	}
}
