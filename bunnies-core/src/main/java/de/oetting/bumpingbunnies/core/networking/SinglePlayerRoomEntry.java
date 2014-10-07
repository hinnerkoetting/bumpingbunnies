package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.network.RoomEntry;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class SinglePlayerRoomEntry extends RoomEntry {

	public SinglePlayerRoomEntry(PlayerProperties playerProperties) {
		super(playerProperties, null, -1);
	}

}
