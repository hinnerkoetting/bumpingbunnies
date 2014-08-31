package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkType;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;

public class GeneralSettingsParceller implements Parceller<GeneralSettings> {

	@Override
	public void writeToParcel(GeneralSettings input, Parcel dest) {
		dest.writeString(input.getWorldConfiguration().toString());
		dest.writeInt(input.getSpeedSetting());
		dest.writeString(input.getNetworkType().toString());
	}

	@Override
	public GeneralSettings createFromParcel(Parcel parcel) {
		WorldConfiguration worldConfiguration = WorldConfiguration.valueOf(parcel.readString());
		int speedSetting = parcel.readInt();
		NetworkType networkType = NetworkType.valueOf(parcel.readString());
		return new GeneralSettings(worldConfiguration, speedSetting, networkType);
	}
}
