package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;

public class ConfigurationParcellableWrapper implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private Configuration configuration;

	public static final Parcelable.Creator<ConfigurationParcellableWrapper> CREATOR = new Parcelable.Creator<ConfigurationParcellableWrapper>() {
		@Override
		public ConfigurationParcellableWrapper createFromParcel(Parcel source) {
			try {
				return new ConfigurationParcellableWrapper(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration %s", e, source.toString());

				throw e;
			}
		}

		@Override
		public ConfigurationParcellableWrapper[] newArray(int size) {
			return new ConfigurationParcellableWrapper[size];
		}
	};

	public ConfigurationParcellableWrapper(Parcel source) {
		configuration = new ConfigurationParceller().createFromParcel(source);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new ConfigurationParceller().writeToParcel(configuration, dest);
	}
}
