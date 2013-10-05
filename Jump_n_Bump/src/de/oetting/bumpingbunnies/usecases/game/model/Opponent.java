package de.oetting.bumpingbunnies.usecases.game.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Opponent implements Parcelable {

	private final String identifier;
	private final boolean myPlayer;

	public static Opponent createMyPlayer(String identifier) {
		return new Opponent(identifier, true);
	}

	public static Opponent createOpponent(String identifier) {
		return new Opponent(identifier, false);
	}

	private Opponent(String identifier, boolean myPlayer) {
		super();
		this.identifier = identifier;
		this.myPlayer = myPlayer;
	}

	public Opponent(Parcel in) {
		this.identifier = in.readString();
		this.myPlayer = in.readInt() == 1;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public boolean isMyPlayer() {
		return this.myPlayer;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.identifier);
		dest.writeInt(this.myPlayer ? 1 : 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
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
		return true;
	}

}
