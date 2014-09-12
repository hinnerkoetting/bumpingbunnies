package de.oetting.bumpingbunnies.configuration.gameStart;

import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;

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
