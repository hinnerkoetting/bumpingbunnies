package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;

public class Configuration implements Parcelable {

	private static MyLog LOGGER = Logger.getLogger(Configuration.class);
	public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() {
		@Override
		public Configuration createFromParcel(Parcel source) {
			try {
				return new Configuration(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading configuration %s", e,
						source.toString());

				throw e;
			}
		}

		@Override
		public Configuration[] newArray(int size) {
			return new Configuration[size];
		}
	};

	private final List<OtherPlayerConfiguration> otherPlayers;
	private final LocalSettings localSettings;
	private final GeneralSettings generalSettings;

	public Configuration(Parcel source) {
		this.localSettings = new LocalSettings(source);
		this.generalSettings = new GeneralSettings(source);
		int numberOtherPlayer = source.readInt();
		this.otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			this.otherPlayers.add(new OtherPlayerConfiguration(source));
		}
	}

	public Configuration(LocalSettings localSettings,
			GeneralSettings generalSettings,
			List<OtherPlayerConfiguration> otherPlayers) {
		this.generalSettings = generalSettings;
		this.otherPlayers = otherPlayers;
		this.localSettings = localSettings;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public InputConfiguration getInputConfiguration() {
		return this.localSettings.getInputConfiguration();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		this.localSettings.writeToParcel(dest, flags);
		this.generalSettings.writeToParcel(dest, flags);
		dest.writeInt(this.otherPlayers.size());
		for (OtherPlayerConfiguration otherPlayer : this.otherPlayers) {
			otherPlayer.writeToParcel(dest, flags);
		}
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.generalSettings.getWorldConfiguration();
	}

	public int getNumberPlayer() {
		return this.otherPlayers.size() + 1;
	}

	public int getZoom() {
		return this.localSettings.getZoom();
	}

	public List<OtherPlayerConfiguration> getOtherPlayers() {
		return this.otherPlayers;
	}

}
