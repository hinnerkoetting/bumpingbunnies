package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;

public class LocalPlayerSettingsParceller implements Parceller<LocalPlayerSettings> {

	@Override
	public void writeToParcel(LocalPlayerSettings input, Parcel parcel) {
		parcel.writeString(input.getPlayerName());
	}

	@Override
	public LocalPlayerSettings createFromParcel(Parcel parcel) {
		return new LocalPlayerSettings(parcel.readString());
	}
}
