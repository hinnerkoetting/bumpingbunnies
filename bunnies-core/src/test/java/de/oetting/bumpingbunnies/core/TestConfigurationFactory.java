package de.oetting.bumpingbunnies.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;

public class TestConfigurationFactory {

	public static Configuration createDummyHost() {
		LocalSettings localSettings = new LocalSettings(new KeyboardInputConfiguration("a", "b", "c"), 1, true, true, false);
		ServerSettings generalSettings = new ServerSettings(WorldConfiguration.CLASSIC, 1, Collections.singleton(NetworkType.WLAN), 10, false);
		List<OpponentConfiguration> opponents = Arrays.asList(new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(0, "name"), OpponentTestFactory
				.create(), new NoopInputConfiguration()));
		LocalPlayerSettings playerSettings = new LocalPlayerSettings("name");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, playerSettings, true);
		return configuration;
	}
}
