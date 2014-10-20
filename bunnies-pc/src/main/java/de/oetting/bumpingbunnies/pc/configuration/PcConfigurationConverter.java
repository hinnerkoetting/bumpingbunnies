package de.oetting.bumpingbunnies.pc.configuration;

import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;
import de.oetting.bumpingbunnies.pc.configMenu.PcConfiguration;
import de.oetting.bumpingbunnies.pc.configMenu.PlayerConfiguration;

public class PcConfigurationConverter {

	public LocalSettings convert2LocalSettings(PcConfiguration configuration) {
		return new LocalSettings(createConfiguration(configuration.getPlayer1Configuration()), 1, true, false);
	}

	public KeyboardInputConfiguration createConfiguration(PlayerConfiguration configuration) {
		KeyboardInputConfiguration inputconfiguration = new KeyboardInputConfiguration(configuration.getPlayerLeft(), configuration.getPlayerUp(),
				configuration.getPlayerRight());
		return inputconfiguration;
	}

	public LocalPlayerSettings convert2LocalPlayerSettings(PcConfiguration configuration) {
		return new LocalPlayerSettings(configuration.getPlayer1Configuration().getPlayerName());
	}

	public ServerSettings convert2ServerSettings(PcConfiguration pcConfiguration) {
		return new ServerSettings(WorldConfiguration.CLASSIC, pcConfiguration.getSpeed(), NetworkType.WLAN);
	}
}
