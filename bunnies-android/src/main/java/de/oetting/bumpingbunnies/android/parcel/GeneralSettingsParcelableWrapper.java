package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public class GeneralSettingsParcelableWrapper implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(ServerSettings.class);

	private final ServerSettings settings;

	public static final Parcelable.Creator<GeneralSettingsParcelableWrapper> CREATOR = new Parcelable.Creator<GeneralSettingsParcelableWrapper>() {
		@Override
		public GeneralSettingsParcelableWrapper createFromParcel(Parcel source) {
			try {
				return new GeneralSettingsParcelableWrapper(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration", e);
				throw e;
			}
		}

		@Override
		public GeneralSettingsParcelableWrapper[] newArray(int size) {
			return new GeneralSettingsParcelableWrapper[size];
		}
	};

	public GeneralSettingsParcelableWrapper(Parcel source) {
		settings = new GeneralSettingsParceller().createFromParcel(source);
	}

	public GeneralSettingsParcelableWrapper(ServerSettings settings) {
		this.settings = settings;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new GeneralSettingsParceller().writeToParcel(settings, dest);
	}

	public ServerSettings getSettings() {
		return settings;
	}
}
