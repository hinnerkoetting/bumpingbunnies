package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerProperties implements Parcelable {

	private final int playerId;
	private final String playerName;

	public PlayerProperties(int playerId, String playerName) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
	}

	public PlayerProperties(Parcel in) {
		super();
		this.playerId = in.readInt();
		this.playerName = in.readString();
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.playerId);
		dest.writeString(this.playerName);
	}

}
