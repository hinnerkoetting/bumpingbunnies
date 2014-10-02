package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;

public class LocalSettingsParcelableWrapper implements Parcelable {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalSettingsParcelableWrapper.class);
	private final LocalSettings localSettings;

	public static final Parcelable.Creator<LocalSettingsParcelableWrapper> CREATOR = new Parcelable.Creator<LocalSettingsParcelableWrapper>() {
		@Override
		public LocalSettingsParcelableWrapper createFromParcel(Parcel source) {
			try {
				return new LocalSettingsParcelableWrapper(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration %s", e, source.toString());

				throw e;
			}
		}

		@Override
		public LocalSettingsParcelableWrapper[] newArray(int size) {
			return new LocalSettingsParcelableWrapper[size];
		}
	};

	public LocalSettingsParcelableWrapper(LocalSettings localSettings) {
		super();
		this.localSettings = localSettings;
	}

	public LocalSettingsParcelableWrapper(Parcel in) {
		localSettings = new LocalSettingsParceller().createFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new LocalSettingsParceller().writeToParcel(getLocalSettings(), dest);
	}

	public LocalSettings getLocalSettings() {
		return localSettings;
	}

}
