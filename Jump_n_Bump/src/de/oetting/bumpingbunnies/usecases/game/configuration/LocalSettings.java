package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;

public class LocalSettings implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(LocalSettings.class);
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
	private final int zoom;

	public LocalSettings(InputConfiguration inputConfiguration, int zoom) {
		super();
		this.inputConfiguration = inputConfiguration;
		this.zoom = zoom;
	}

	public LocalSettings(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
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
		dest.writeInt(this.zoom);
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public int getZoom() {
		return this.zoom;
	}

}
