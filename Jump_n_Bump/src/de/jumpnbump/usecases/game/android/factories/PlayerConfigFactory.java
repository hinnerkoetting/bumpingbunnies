package de.jumpnbump.usecases.game.android.factories;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.android.calculation.RelativeCoordinatesCalculation;
import de.jumpnbump.usecases.game.businesslogic.AllPlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;
import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

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
		// TODO so ist es richtig
		// List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		// for (OtherPlayerConfiguration config :
		// configuration.getOtherPlayers()) {
		// Player p = PlayerFactory.createPlayer(config.getPlayerId());
		// list.add(createPlayerConfig(p, world, config));
		// }
		if (configuration.getOtherPlayers().size() > 2) {
			// TODO
			throw new IllegalArgumentException("TODO");
		}
		if (myPlayerId == 0) {
			OtherPlayerConfiguration config = configuration.getOtherPlayers()
					.get(0);

			Player p = PlayerFactory.createPlayer(1);
			world.addPlayer(p);
			list.add(createPlayerConfig(p, world, config));
		} else {
			OtherPlayerConfiguration config = configuration.getOtherPlayers()
					.get(0);

			Player p = PlayerFactory.createPlayer(0);
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
