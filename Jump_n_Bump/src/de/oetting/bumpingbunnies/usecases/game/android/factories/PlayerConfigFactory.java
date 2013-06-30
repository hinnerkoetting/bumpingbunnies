package de.oetting.bumpingbunnies.usecases.game.android.factories;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerMovementFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerConfigFactory {

	public static AllPlayerConfig create(Intent intent, World world,
			GameView gameView, AbstractOtherPlayersFactory otherPlayerFactory,
			Configuration configuration) {
		int myPlayerId = findTabletPlayerId(intent);
		Player myPlayer = findMyPlayer(myPlayerId, world);
		world.addPlayer(myPlayer);
		CoordinatesCalculation calculations = createCoordinateCalculations(myPlayer);
		PlayerMovementController myPlayerMovementController = createMovementController(
				myPlayer, world);
		List<PlayerConfig> otherPlayerconfigs = findOtherPlayers(
				otherPlayerFactory, myPlayerId, world, configuration);
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
			AbstractOtherPlayersFactory otherPlayerFactory, int myPlayerId,
			World world, Configuration configuration) {
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		for (OtherPlayerConfiguration config : configuration.getOtherPlayers()) {
			Player p = PlayerFactory.createPlayer(config.getPlayerId());
			world.addPlayer(p);
			list.add(createPlayerConfig(p, world, config));
		}
		return list;
	}

	private static PlayerConfig createPlayerConfig(Player player, World world,
			OtherPlayerConfiguration configuration) {
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

	private static Player findMyPlayer(int myPlayerId, World world) {
		return PlayerFactory.createPlayer(myPlayerId);
	}

	private static int findTabletPlayerId(Intent intent) {
		if (intent.getExtras() != null) {
			GameStartParameter parameter = intent.getExtras().getParcelable(
					ActivityLauncher.GAMEPARAMETER);
			return parameter.getPlayerId();
		} else {
			return 0;
		}
	}
}
