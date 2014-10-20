package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class LocalPlayerEntry extends RoomEntry {

	public LocalPlayerEntry(PlayerProperties playerProperties) {
		super(playerProperties, ConnectionIdentifierFactory.createLocalPlayer(playerProperties.getPlayerName()));
	}

}
