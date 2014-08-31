package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public class PlayerPropertiesParceller implements Parceller<PlayerProperties> {

	@Override
	public void writeToParcel(PlayerProperties input, Parcel parcel) {
		parcel.writeInt(input.getPlayerId());
		parcel.writeString(input.getPlayerName());
	}

	@Override
	public PlayerProperties createFromParcel(Parcel parcel) {
		int playerId = parcel.readInt();
		String playerName = parcel.readString();
		return new PlayerProperties(playerId, playerName);
	}

}
