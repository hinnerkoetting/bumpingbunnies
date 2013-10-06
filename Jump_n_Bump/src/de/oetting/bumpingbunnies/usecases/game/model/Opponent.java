package de.oetting.bumpingbunnies.usecases.game.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Opponent implements Parcelable {

	private final String identifier;
	private final OpponentType type;

	public static Opponent createMyPlayer(String identifier) {
		return new Opponent(identifier, OpponentType.MY_PLAYER);
	}

	public static Opponent createOpponent(String identifier, OpponentType type) {
		return new Opponent(identifier, type);
	}

	private Opponent(String identifier, OpponentType type) {
		super();
		this.identifier = identifier;
		this.type = type;
	}

	public Opponent(Parcel in) {
		this.identifier = in.readString();
		this.type = OpponentType.valueOf(in.readString());
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean isMyPlayer() {
		return OpponentType.MY_PLAYER == this.type;
	}

	public boolean isLocalPlayer() {
		return OpponentType.MY_PLAYER == this.type || OpponentType.AI == this.type;
	}

	public OpponentType getType() {
		return this.type;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.identifier);
		dest.writeString(this.type.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Opponent other = (Opponent) obj;
		if (this.identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		} else if (!this.identifier.equals(other.identifier)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
	}

}
