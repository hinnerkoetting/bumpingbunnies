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
	private final WorldConfiguration worldConfiguration;
	private final int numberPlayers;

	public Configuration(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
		this.aiModus = AiModus.valueOf(source.readString());
		this.worldConfiguration = WorldConfiguration.valueOf(source
				.readString());
		this.numberPlayers = source.readInt();
	}

	public Configuration(InputConfiguration inputConfiguration,
			AiModus aiModus, WorldConfiguration worldConfiguration,
			int numberPlayer) {
		this.inputConfiguration = inputConfiguration;
		this.aiModus = aiModus;
		this.worldConfiguration = worldConfiguration;
		this.numberPlayers = numberPlayer;
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
		dest.writeString(this.worldConfiguration.toString());
		dest.writeInt(this.numberPlayers);
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

	public int getNumberPlayer() {
		return this.numberPlayers;
	}
}
