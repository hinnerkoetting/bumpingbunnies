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

	private final InputConfiguration inputConfiguration;
	private final AiModus aiModus;

	public Configuration(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
		this.aiModus = AiModus.valueOf(source.readString());
	}

	public Configuration(InputConfiguration inputConfiguration, AiModus aiModus) {
		this.inputConfiguration = inputConfiguration;
		this.aiModus = aiModus;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	public AiModus getAiModus() {
		return this.aiModus;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.inputConfiguration.toString());
		dest.writeString(this.aiModus.toString());
	}

}
