package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;

public class ConfigurationParcelableWrapper implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationParcelableWrapper.class);
	public static final Parcelable.Creator<ConfigurationParcelableWrapper> CREATOR = new Parcelable.Creator<ConfigurationParcelableWrapper>() {
		@Override
		public ConfigurationParcelableWrapper createFromParcel(Parcel source) {
			try {
				Configuration config = new ConfigurationParceller().createFromParcel(source);
				return new ConfigurationParcelableWrapper(config);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration %s", e, source.toString());
				throw e;
			}
		}

		@Override
		public ConfigurationParcelableWrapper[] newArray(int size) {
			return new ConfigurationParcelableWrapper[size];
		}
	};

	private final Configuration configuration;

	public ConfigurationParcelableWrapper(Configuration configuration) {
		super();
		this.configuration = configuration;
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
