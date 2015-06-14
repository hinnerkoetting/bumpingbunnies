package de.oetting.bumpingbunnies.android.parcel;

import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

public class GeneralSettingsParceller implements Parceller<ServerSettings> {

	@Override
	public void writeToParcel(ServerSettings input, Parcel dest) {
		dest.writeString(input.getWorldConfiguration().toString());
		dest.writeInt(input.getSpeedSetting());
		dest.writeInt(input.getNetworkTypes().size());
		for (NetworkType type : input.getNetworkTypes())
			dest.writeString(type.toString());
		dest.writeInt(input.getVictoryLimit());
	}

	@Override
	public ServerSettings createFromParcel(Parcel parcel) {
		WorldConfiguration worldConfiguration = WorldConfiguration.valueOf(parcel.readString());
		int speedSetting = parcel.readInt();
		int numberOfNetworkTypes = parcel.readInt();
		Set<NetworkType> networkTypes = new HashSet<NetworkType>(numberOfNetworkTypes);
		for (int i = 0; i < numberOfNetworkTypes; i++)
			networkTypes.add(NetworkType.valueOf(parcel.readString()));
		int victoryLimit = parcel.readInt();
		return new ServerSettings(worldConfiguration, speedSetting, networkTypes, victoryLimit);
	}
}
