package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

/**
 * Which which are valid for all players.
 * 
 */
public class GeneralSettings implements Parcelable {

	private static Logger LOGGER = LoggerFactory
			.getLogger(GeneralSettings.class);
	public static final Parcelable.Creator<GeneralSettings> CREATOR = new Parcelable.Creator<GeneralSettings>() {
		@Override
		public GeneralSettings createFromParcel(Parcel source) {
			try {
				return new GeneralSettings(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration", e);
				throw e;
			}
		}

		@Override
		public GeneralSettings[] newArray(int size) {
			return new GeneralSettings[size];
		}
	};

	private final WorldConfiguration worldConfiguration;
	private final int speedSetting;
	private final NetworkType networkType;

	public GeneralSettings(WorldConfiguration worldConfiguration,
			int speedSetting, NetworkType networkType) {
		super();
		this.worldConfiguration = worldConfiguration;
		this.speedSetting = speedSetting;
		this.networkType = networkType;
	}

	public GeneralSettings(Parcel source) {
		this.worldConfiguration = WorldConfiguration.valueOf(source
				.readString());
		this.speedSetting = source.readInt();
		this.networkType = NetworkType.valueOf(source.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.worldConfiguration.toString());
		dest.writeInt(this.speedSetting);
		dest.writeString(this.networkType.toString());
	}

	public int getSpeedSetting() {
		return this.speedSetting;
	}

	public NetworkType getNetworkType() {
		return this.networkType;
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

}
