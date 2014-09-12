package de.oetting.bumpingbunnies.core.configuration;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerConfigFactory {

	public static Player createMyPlayer(GameStartParameter parameter) {
		return findMyPlayer(parameter);
	}

	public static List<PlayerConfig> createOtherPlayers(Configuration configuration) {
		int speed = configuration.getGeneralSettings().getSpeedSetting();
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		for (OpponentConfiguration config : configuration.getOtherPlayers()) {
			Player p = playerfactory.createPlayer(config.getPlayerId(), config.getName(), config.getOpponent());
			list.add(createPlayerConfig(p, config));
		}
		return list;
	}

	private static PlayerConfig createPlayerConfig(Player player, OpponentConfiguration configuration) {
		AiModus aiMode = configuration.getAiMode();
		return new PlayerConfig(aiMode, player, configuration);
	}

	private static Player findMyPlayer(GameStartParameter gameParameter) {
		int speed = gameParameter.getConfiguration().getGeneralSettings().getSpeedSetting();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		return playerfactory.createPlayer(gameParameter.getPlayerId(), gameParameter.getConfiguration().getLocalPlayerSettings()
				.getPlayerName(), Opponent.createMyPlayer("LOCAL-PLAYER"));
	}
}