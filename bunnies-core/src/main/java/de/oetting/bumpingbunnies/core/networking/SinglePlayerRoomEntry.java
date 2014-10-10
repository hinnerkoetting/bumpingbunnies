package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.network.room.RoomEntry;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.OpponentFactory;

public class SinglePlayerRoomEntry extends RoomEntry {

	public SinglePlayerRoomEntry(PlayerProperties playerProperties) {
		super(playerProperties, OpponentFactory.createLocalPlayer(playerProperties.getPlayerName()));
	}

}
