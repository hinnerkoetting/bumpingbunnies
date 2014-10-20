package de.oetting.bumpingbunnies.core.configuration;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.OpponentFactory;
import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Player;

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
			list.add(createPlayerConfig(p, config, config.getInput()));
		}
		return list;
	}

	public static PlayerConfig createPlayerConfig(Player player, OpponentConfiguration configuration, InputConfiguration input) {
		AiModus aiMode = configuration.getAiMode();
		return new PlayerConfig(configuration, aiMode, player, input);
	}

	private static Player findMyPlayer(GameStartParameter gameParameter) {
		int speed = gameParameter.getConfiguration().getGeneralSettings().getSpeedSetting();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		String playerName = gameParameter.getConfiguration().getLocalPlayerSettings().getPlayerName();
		return playerfactory.createPlayer(gameParameter.getPlayerId(), playerName, OpponentFactory.createLocalPlayer(playerName));
	}
}
