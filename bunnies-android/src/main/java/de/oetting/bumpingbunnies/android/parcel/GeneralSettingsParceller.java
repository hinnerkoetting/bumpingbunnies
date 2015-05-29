package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

public class GeneralSettingsParceller implements Parceller<ServerSettings> {

	@Override
	public void writeToParcel(ServerSettings input, Parcel dest) {
		dest.writeString(input.getWorldConfiguration().toString());
		dest.writeInt(input.getSpeedSetting());
		dest.writeString(input.getNetworkType().toString());
		dest.writeInt(input.getVictoryLimit());
	}

	@Override
	public ServerSettings createFromParcel(Parcel parcel) {
		WorldConfiguration worldConfiguration = WorldConfiguration.valueOf(parcel.readString());
		int speedSetting = parcel.readInt();
		NetworkType networkType = NetworkType.valueOf(parcel.readString());
		int victoryLimit = parcel.readInt();
		return new ServerSettings(worldConfiguration, speedSetting, networkType, victoryLimit);
	}
}
