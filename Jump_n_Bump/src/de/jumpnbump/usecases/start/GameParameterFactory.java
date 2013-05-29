package de.jumpnbump.usecases.start;

import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.configuration.Configuration;

public class GameParameterFactory {

	public static GameStartParameter createParameter(int playerId,
			Configuration configuration) {
		return new GameStartParameter(configuration, playerId);
	}

	public static GameStartParameter createSingleplayerParameter(
			Configuration configuration) {
		return new GameStartParameter(configuration,
				ActivityLauncher.SINGPLE_PLAYER_ID);
	}

}
