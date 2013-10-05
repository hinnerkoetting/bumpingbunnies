package de.oetting.bumpingbunnies.usecases.game.android.factories;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerMovementFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerConfigFactory {

	public static PlayerMovement createMyPlayer(GameStartParameter parameter) {
		Player myPlayer = findMyPlayer(parameter);
		PlayerMovement myPlayerMovementController = createMovementController(myPlayer);
		return myPlayerMovementController;
	}

	public static List<PlayerConfig> findOtherPlayers(
			Configuration configuration) {
		int speed = configuration.getGeneralSettings().getSpeedSetting();
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		for (OpponentConfiguration config : configuration.getOtherPlayers()) {
			Player p = playerfactory.createPlayer(config.getPlayerId(), config.getName(), config.getOpponent());
			list.add(createPlayerConfig(p, config));
		}
		return list;
	}

	private static PlayerConfig createPlayerConfig(Player player,
			OpponentConfiguration configuration) {
		AiModus aiMode = configuration.getAiMode();
		PlayerMovement movementcontroller = createMovementController(player);
		return new PlayerConfig(aiMode, movementcontroller, configuration);
	}

	private static PlayerMovement createMovementController(Player p) {
		return PlayerMovementFactory.create(p);
	}

	private static Player findMyPlayer(GameStartParameter gameParameter) {
		int speed = gameParameter.getConfiguration().getGeneralSettings()
				.getSpeedSetting();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		return playerfactory.createPlayer(gameParameter.getPlayerId(),
				gameParameter.getConfiguration().getLocalPlayerSettings()
						.getPlayerName(), Opponent.createMyPlayer("LOCAL-PLAYER"));
	}
}
