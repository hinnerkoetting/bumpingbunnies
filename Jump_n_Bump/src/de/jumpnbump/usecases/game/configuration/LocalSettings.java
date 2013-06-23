package de.jumpnbump.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class LocalSettings implements Parcelable {

	private static MyLog LOGGER = Logger.getLogger(LocalSettings.class);
	public static final Parcelable.Creator<LocalSettings> CREATOR = new Parcelable.Creator<LocalSettings>() {
		@Override
		public LocalSettings createFromParcel(Parcel source) {
			try {
				return new LocalSettings(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration", e);
				throw e;
			}
		}

		@Override
		public LocalSettings[] newArray(int size) {
			return new LocalSettings[size];
		}
	};

	private final InputConfiguration inputConfiguration;
	/**
	 * TODO Put into other settings because its not local
	 */
	private final WorldConfiguration worldConfiguration;
	private final int zoom;

	public LocalSettings(InputConfiguration inputConfiguration,
			WorldConfiguration worldConfiguration, int zoom) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.worldConfiguration = worldConfiguration;
		this.zoom = zoom;
	}

	public LocalSettings(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
		this.worldConfiguration = WorldConfiguration.valueOf(source
				.readString());
		this.zoom = source.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.inputConfiguration.toString());
		dest.writeString(this.worldConfiguration.toString());
		dest.writeInt(this.zoom);
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

	public int getZoom() {
		return this.zoom;
	}

}
