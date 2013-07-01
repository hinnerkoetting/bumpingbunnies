package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;

public class NetworkSettings implements Parcelable {

	private final boolean host;

	public NetworkSettings(boolean host) {
		super();
		this.host = host;
	}

	public NetworkSettings(Parcel parcel) {
		this.host = Boolean.valueOf(parcel.readString());
	}

	public boolean isHost() {
		return this.host;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Boolean.toString(this.host));

	}

}
