package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;

public class LocalPlayerSettingsParcellableWrapper implements Parcelable {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalPlayerSettings.class);
	public static final Parcelable.Creator<LocalPlayerSettingsParcellableWrapper> CREATOR = new Parcelable.Creator<LocalPlayerSettingsParcellableWrapper>() {
		@Override
		public LocalPlayerSettingsParcellableWrapper createFromParcel(Parcel source) {
			try {
				return new LocalPlayerSettingsParcellableWrapper(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading local player settings", e);
				throw e;
			}
		}

		@Override
		public LocalPlayerSettingsParcellableWrapper[] newArray(int size) {
			return new LocalPlayerSettingsParcellableWrapper[size];
		}
	};

	private final LocalPlayerSettings settings;

	public LocalPlayerSettingsParcellableWrapper(Parcel source) {
		settings = new LocalPlayerSettingsParceller().createFromParcel(source);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new LocalPlayerSettingsParceller().writeToParcel(settings, dest);
	}

	public LocalPlayerSettings getSettings() {
		return settings;
	}

}
