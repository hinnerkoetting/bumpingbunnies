package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class Configuration implements Parcelable {

	private static Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
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

	private final List<OpponentConfiguration> otherPlayers;
	private final LocalSettings localSettings;
	private final GeneralSettings generalSettings;
	private final LocalPlayersettings localPlayerSettings;

	public Configuration(Parcel source) {
		this.localSettings = new LocalSettings(source);
		this.generalSettings = new GeneralSettings(source);
		this.localPlayerSettings = new LocalPlayersettings(source);
		int numberOtherPlayer = source.readInt();
		this.otherPlayers = new ArrayList<OpponentConfiguration>(
				numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			this.otherPlayers.add(new OpponentConfiguration(source));
		}
	}

	public Configuration(LocalSettings localSettings,
			GeneralSettings generalSettings,
			List<OpponentConfiguration> otherPlayers,
			LocalPlayersettings localPlayersettings) {
		this.generalSettings = generalSettings;
		this.otherPlayers = otherPlayers;
		this.localSettings = localSettings;
		this.localPlayerSettings = localPlayersettings;
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
		this.localPlayerSettings.writeToParcel(dest, flags);
		dest.writeInt(this.otherPlayers.size());
		for (OpponentConfiguration otherPlayer : this.otherPlayers) {
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

	public List<OpponentConfiguration> getOtherPlayers() {
		return this.otherPlayers;
	}

	public GeneralSettings getGeneralSettings() {
		return this.generalSettings;
	}

	public LocalPlayersettings getLocalPlayerSettings() {
		return this.localPlayerSettings;
	}

}
