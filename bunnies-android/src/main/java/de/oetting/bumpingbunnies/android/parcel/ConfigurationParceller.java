package de.oetting.bumpingbunnies.android.parcel;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;

public class ConfigurationParceller implements Parceller<Configuration> {

	@Override
	public void writeToParcel(Configuration input, Parcel dest) {
		new LocalSettingsParceller().writeToParcel(input.getLocalSettings(), dest);
		new GeneralSettingsParceller().writeToParcel(input.getGeneralSettings(), dest);
		new LocalPlayerSettingsParceller().writeToParcel(input.getLocalPlayerSettings(), dest);
		dest.writeInt(input.isHost() ? 1 : 0);
		dest.writeInt(input.getOtherPlayers().size());
		for (OpponentConfiguration otherPlayer : input.getOtherPlayers()) {
			new OpponentConfigurationParceller().writeToParcel(otherPlayer, dest);
		}
	}

	@Override
	public Configuration createFromParcel(Parcel source) {
		LocalSettings localSettings = new LocalSettingsParceller().createFromParcel(source);
		ServerSettings generalSettings = new GeneralSettingsParceller().createFromParcel(source);
		LocalPlayerSettings localPlayerSettings = new LocalPlayerSettingsParceller().createFromParcel(source);
		boolean host = source.readInt() == 1;
		int numberOtherPlayer = source.readInt();
		List<OpponentConfiguration> otherPlayers = new ArrayList<OpponentConfiguration>(numberOtherPlayer);
		for (int i = 0; i < numberOtherPlayer; i++) {
			otherPlayers.add(new OpponentConfigurationParceller().createFromParcel(source));
		}
		return new Configuration(localSettings, generalSettings, otherPlayers, localPlayerSettings, host);
	}
}
