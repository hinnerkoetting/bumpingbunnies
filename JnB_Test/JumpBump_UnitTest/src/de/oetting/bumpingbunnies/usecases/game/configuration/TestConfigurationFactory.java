package de.oetting.bumpingbunnies.usecases.game.configuration;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent.OpponentType;

public class TestConfigurationFactory {

	public static Configuration createDummyHost() {
		LocalSettings localSettings = new LocalSettings(InputConfiguration.ANALOG, 1, true, true);
		GeneralSettings generalSettings = new GeneralSettings(
				WorldConfiguration.CASTLE, 1);
		List<OpponentConfiguration> opponents = Arrays.asList(new OpponentConfiguration(AiModus.NORMAL,
				new PlayerProperties(0, "name"), Opponent.createOpponent("identifier", OpponentType.AI)));
		LocalPlayersettings playerSettings = new LocalPlayersettings("name");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, playerSettings, true);
		return configuration;
	}
}
