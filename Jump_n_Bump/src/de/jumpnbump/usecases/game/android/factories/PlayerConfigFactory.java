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
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfigFactory {

	public static AllPlayerConfig create(Intent intent, World world,
			GameView gameView, AbstractOtherPlayersFactory otherPlayerFactory,
			Configuration configuration) {
		int myPlayerId = findTabletPlayerId(intent);
		Player myPlayer = findMyPlayer(myPlayerId, world);
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

		for (int i = 0; i < configuration.getOtherPlayers().size(); i++) {
			OtherPlayerConfiguration config = configuration.getOtherPlayers()
					.get(i);
			// TODO
			Player p = world.getAllPlayer().get(i + 1);
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
		for (Player player : world.getAllPlayer()) {
			if (player.id() == myPlayerId) {
				return player;
			}
		}
		throw new IllegalArgumentException("Could not find my player with id "
				+ myPlayerId);

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
