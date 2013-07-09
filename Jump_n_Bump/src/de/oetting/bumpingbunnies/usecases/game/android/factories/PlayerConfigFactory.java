package de.oetting.bumpingbunnies.usecases.game.android.factories;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerMovementFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerConfigFactory {

	public static AllPlayerConfig create(GameStartParameter parameter,
			World world, GameView gameView,
			AbstractOtherPlayersFactory otherPlayerFactory) {

		Player myPlayer = findMyPlayer(parameter, world);
		world.addPlayer(myPlayer);
		CoordinatesCalculation calculations = createCoordinateCalculations(myPlayer);
		PlayerMovementController myPlayerMovementController = createMovementController(
				myPlayer, world);
		List<PlayerConfig> otherPlayerconfigs = findOtherPlayers(
				otherPlayerFactory, world, parameter.getConfiguration());
		AllPlayerConfig config = new AllPlayerConfig(
				myPlayerMovementController, otherPlayerconfigs, gameView,
				world, calculations);
		return config;
	}

	private static CoordinatesCalculation createCoordinateCalculations(
			Player myPlayer) {
		return new RelativeCoordinatesCalculation(myPlayer);
	}

	private static List<PlayerConfig> findOtherPlayers(
			AbstractOtherPlayersFactory otherPlayerFactory, World world,
			Configuration configuration) {
		int speed = configuration.getGeneralSettings().getSpeedSetting();
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		for (OpponentConfiguration config : configuration.getOtherPlayers()) {
			Player p = playerfactory.createPlayer(config.getPlayerId(),
					config.getName());
			world.addPlayer(p);
			list.add(createPlayerConfig(p, world, config, speed));
		}
		return list;
	}

	private static PlayerConfig createPlayerConfig(Player player, World world,
			OpponentConfiguration configuration, int speedFactor) {
		AbstractOtherPlayersFactory otherPlayerFactory = configuration
				.getFactory();
		PlayerMovementController movementcontroller = createMovementController(
				player, world);
		return new PlayerConfig(otherPlayerFactory, movementcontroller, world,
				configuration);
	}

	private static PlayerMovementController createMovementController(Player p,
			World world) {
		return PlayerMovementFactory.create(p, world);
	}

	private static Player findMyPlayer(GameStartParameter gameParameter,
			World world) {
		int speed = gameParameter.getConfiguration().getGeneralSettings()
				.getSpeedSetting();
		PlayerFactory playerfactory = new PlayerFactory(speed);
		return playerfactory.createPlayer(gameParameter.getPlayerId(),
				gameParameter.getConfiguration().getLocalPlayerSettings()
						.getPlayerName());
	}
}
