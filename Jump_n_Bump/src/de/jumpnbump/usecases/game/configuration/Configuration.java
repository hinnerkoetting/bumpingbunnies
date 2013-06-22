package de.jumpnbump.usecases.game.configuration;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Configuration implements Parcelable {

	private static MyLog LOGGER = Logger.getLogger(Configuration.class);
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
	private final WorldConfiguration worldConfiguration;
	private final List<OtherPlayerConfiguration> otherPlayers;
	private final int zoom;

	public Configuration(Parcel source) {
		this.inputConfiguration = InputConfiguration.valueOf(source
				.readString());
		this.worldConfiguration = WorldConfiguration.valueOf(source
				.readString());
		int numberOtherPlayer = source.readInt();
		this.otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			this.otherPlayers.add(new OtherPlayerConfiguration(source));
		}
		this.zoom = source.readInt();
		log();
	}

	public Configuration(InputConfiguration inputConfiguration,
			WorldConfiguration worldConfiguration,
			List<OtherPlayerConfiguration> otherPlayers, int zoom) {
		this.inputConfiguration = inputConfiguration;
		this.worldConfiguration = worldConfiguration;
		this.otherPlayers = otherPlayers;
		this.zoom = zoom;
		log();
	}

	private void log() {
		LOGGER.info("%s - %s - Players: %d - Zoom: %d",
				this.inputConfiguration.toString(),
				this.worldConfiguration.toString(), this.otherPlayers.size(),
				this.zoom);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public InputConfiguration getInputConfiguration() {
		return this.inputConfiguration;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.inputConfiguration.toString());
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
		return this.otherPlayers.size() + 1;
	}

	public int getZoom() {
		return this.zoom;
	}

	public List<OtherPlayerConfiguration> getOtherPlayers() {
		return this.otherPlayers;
	}

}
