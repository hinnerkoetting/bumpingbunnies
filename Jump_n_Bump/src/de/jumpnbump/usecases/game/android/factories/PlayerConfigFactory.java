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
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactorySingleton;
import de.jumpnbump.usecases.game.factories.PlayerMovementFactory;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfigFactory {

	public static AllPlayerConfig create(Intent intent, World world,
			GameView gameView,
			AbstractOtherPlayersFactorySingleton otherPlayerFactory) {
		int myPlayerId = findTabletPlayerId(intent);
		Player myPlayer = findMyPlayer(myPlayerId, world);
		CoordinatesCalculation calculations = createCoordinateCalculations(myPlayer);
		AllPlayerConfig config = new AllPlayerConfig(createMovementController(
				myPlayer, world), findOtherPlayers(otherPlayerFactory,
				myPlayerId, world), gameView, world, calculations);
		return config;
	}

	private static CoordinatesCalculation createCoordinateCalculations(
			Player myPlayer) {
		return new RelativeCoordinatesCalculation(myPlayer);
	}

	private static List<PlayerConfig> findOtherPlayers(
			AbstractOtherPlayersFactorySingleton otherPlayerFactory,
			int myPlayerId, World world) {
		List<PlayerConfig> list = new LinkedList<PlayerConfig>();
		for (Player p : world.getAllPlayer()) {
			if (p.id() != myPlayerId) {
				list.add(createPlayerConfig(otherPlayerFactory, p, world));
			}
		}
		return list;
	}

	private static PlayerConfig createPlayerConfig(
			AbstractOtherPlayersFactorySingleton otherPlayerFactory,
			Player player, World world) {
		PlayerMovementController movementcontroller = createMovementController(
				player, world);
		return new PlayerConfig(otherPlayerFactory, movementcontroller, world);
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
