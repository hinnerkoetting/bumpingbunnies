package de.oetting.bumpingbunnies.usecases.start;

import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

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
