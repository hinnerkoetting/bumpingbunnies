package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.android.input.analog.AnalogInputConfiguration;
import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.input.NoopInputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

public class TestConfigurationFactory {

	public static Configuration createDummyHost() {
		LocalSettings localSettings = new LocalSettings(new AnalogInputConfiguration(), 1, true, true, true, true);
		ServerSettings generalSettings = new ServerSettings(WorldConfiguration.CASTLE, 1, NetworkType.WLAN);
		List<OpponentConfiguration> opponents = Arrays.asList(new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(0, "name"), OpponentTestFactory
				.create(), new NoopInputConfiguration()));
		LocalPlayerSettings playerSettings = new LocalPlayerSettings("name");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, playerSettings, true);
		return configuration;
	}
}
