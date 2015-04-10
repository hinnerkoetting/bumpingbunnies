package de.oetting.bumpingbunnies.core.configuration;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PlayerConfigFactory {

	public static Bunny createMyPlayer(GameStartParameter parameter) {
		return findMyPlayer(parameter);
	}

	public static List<PlayerConfig> createOtherPlayers(Configuration configuration) {
		int speed = configuration.getGeneralSettings().getSpeedSetting();
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		BunnyFactory playerfactory = new BunnyFactory(speed);
		for (OpponentConfiguration config : configuration.getOtherPlayers()) {
			Bunny p = playerfactory.createPlayer(config.getPlayerId(), config.getName(), config.getOpponent());
			list.add(createPlayerConfig(p, config, config.getInput()));
		}
		return list;
	}

	public static PlayerConfig createPlayerConfig(Bunny player, OpponentConfiguration configuration, InputConfiguration input) {
		AiModus aiMode = configuration.getAiMode();
		return new PlayerConfig(configuration, aiMode, player, input);
	}

	private static Bunny findMyPlayer(GameStartParameter gameParameter) {
		int speed = gameParameter.getConfiguration().getGeneralSettings().getSpeedSetting();
		BunnyFactory playerfactory = new BunnyFactory(speed);
		String playerName = gameParameter.getConfiguration().getLocalPlayerSettings().getPlayerName();
		return playerfactory.createPlayer(gameParameter.getPlayerId(), playerName, ConnectionIdentifierFactory.createLocalPlayer(playerName));
	}
}
