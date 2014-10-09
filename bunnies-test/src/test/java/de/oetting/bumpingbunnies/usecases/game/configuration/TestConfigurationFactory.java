package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class TestConfigurationFactory {

	public static Configuration createDummyHost() {
		LocalSettings localSettings = new LocalSettings(InputConfiguration.ANALOG, 1, true, true);
		ServerSettings generalSettings = new ServerSettings(
				WorldConfiguration.CASTLE, 1, NetworkType.WLAN);
		List<OpponentConfiguration> opponents = Arrays.asList(new OpponentConfiguration(AiModus.NORMAL,
				new PlayerProperties(0, "name"), Opponent.createOpponent("identifier", OpponentType.AI)));
		LocalPlayerSettings playerSettings = new LocalPlayerSettings("name");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, playerSettings, true);
		return configuration;
	}
}
