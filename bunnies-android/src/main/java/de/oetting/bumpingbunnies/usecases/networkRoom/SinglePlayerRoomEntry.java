package de.oetting.bumpingbunnies.usecases.networkRoom;

import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class SinglePlayerRoomEntry extends RoomEntry {

	public SinglePlayerRoomEntry(PlayerProperties playerProperties) {
		super(playerProperties, null, -1);
	}

}
