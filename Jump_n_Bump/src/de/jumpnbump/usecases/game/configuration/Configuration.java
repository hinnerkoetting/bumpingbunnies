package de.jumpnbump.usecases.game.configuration;

import java.util.ArrayList;
import java.util.List;

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
	private final List<OtherPlayerConfiguration> otherPlayers;
	private final int zoom;

	public Configuration(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
		this.aiModus = AiModus.valueOf(source.readString());
		this.worldConfiguration = WorldConfiguration.valueOf(source
				.readString());
		int numberOtherPlayer = source.readInt();
		this.otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			this.otherPlayers.add(new OtherPlayerConfiguration(source));
		}
		this.zoom = source.readInt();
	}

	public Configuration(InputConfiguration inputConfiguration,
			AiModus aiModus, WorldConfiguration worldConfiguration,
			List<OtherPlayerConfiguration> otherPlayers, int zoom) {
		this.inputConfiguration = inputConfiguration;
		this.aiModus = aiModus;
		this.worldConfiguration = worldConfiguration;
		this.otherPlayers = otherPlayers;
		this.zoom = zoom;
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
		dest.writeInt(this.otherPlayers.size());
		for (OtherPlayerConfiguration otherPlayer : this.otherPlayers) {
			otherPlayer.writeToParcel(dest, flags);
		}
		dest.writeInt(this.zoom);
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

	public int getNumberPlayer() {
		return this.otherPlayers.size();
	}

	public int getZoom() {
		return this.zoom;
	}

	public List<OtherPlayerConfiguration> getOtherPlayers() {
		return this.otherPlayers;
	}

}
