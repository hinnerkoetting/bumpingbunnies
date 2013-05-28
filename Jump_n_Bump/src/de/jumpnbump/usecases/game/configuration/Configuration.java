package de.jumpnbump.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;

public class Configuration implements Parcelable {

	public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() {
		@Override
		public Configuration createFromParcel(Parcel source) {
			return new Configuration(source);
		}

		@Override
		public Configuration[] newArray(int size) {
			return new Configuration[size];
		}
	};

	private InputConfiguration inputConfiguration;

	public Configuration(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
	}

	public Configuration(InputConfiguration inputConfiguration) {
		this.inputConfiguration = inputConfiguration;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public void setInputConfiguration(InputConfiguration inputConfiguration) {
		this.inputConfiguration = inputConfiguration;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.inputConfiguration.toString());
	}

}
