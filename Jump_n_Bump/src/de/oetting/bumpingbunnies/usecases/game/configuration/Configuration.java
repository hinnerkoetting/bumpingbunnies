package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.android.parcel.GeneralSettingsParceller;
import de.oetting.bumpingbunnies.android.parcel.LocalSettingsParceller;
import de.oetting.bumpingbunnies.android.parcel.OpponentConfigurationParceller;
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
				LOGGER.error("Exception during reading configuration %s", e, source.toString());

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
	private final boolean host;

	public Configuration(Parcel source) {
		this.localSettings = new LocalSettingsParceller().createFromParcel(source);
		this.generalSettings = new GeneralSettingsParceller().createFromParcel(source);
		this.localPlayerSettings = new LocalPlayersettings(source);
		this.host = source.readInt() == 1;
		int numberOtherPlayer = source.readInt();
		this.otherPlayers = new ArrayList<OpponentConfiguration>(numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			this.otherPlayers.add(new OpponentConfigurationParceller().createFromParcel(source));
		}
	}

	public Configuration(LocalSettings localSettings, GeneralSettings generalSettings, List<OpponentConfiguration> otherPlayers,
			LocalPlayersettings localPlayersettings, boolean host) {
		this.generalSettings = generalSettings;
		this.otherPlayers = otherPlayers;
		this.localSettings = localSettings;
		this.localPlayerSettings = localPlayersettings;
		this.host = host;
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
		new LocalSettingsParceller().writeToParcel(localSettings, dest);
		new GeneralSettingsParceller().writeToParcel(generalSettings, dest);
		this.localPlayerSettings.writeToParcel(dest, flags);
		dest.writeInt(this.host ? 1 : 0);
		dest.writeInt(this.otherPlayers.size());
		for (OpponentConfiguration otherPlayer : this.otherPlayers) {
			new OpponentConfigurationParceller().writeToParcel(otherPlayer, dest);
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

	public LocalSettings getLocalSettings() {
		return this.localSettings;
	}

	public boolean isHost() {
		return this.host;
	}

}
