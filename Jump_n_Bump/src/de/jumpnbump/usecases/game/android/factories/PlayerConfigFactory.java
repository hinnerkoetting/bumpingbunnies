package de.jumpnbump.usecases.game.android.factories;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.PlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.CollisionDetection;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.factories.CollisionDetectionFactory;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfigFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(PlayerConfigFactory.class);

	public static PlayerConfig create(Intent intent, World world,
			GameView gameView) {
		CollisionDetection collision = CollisionDetectionFactory.create(world,
				gameView);
		int myPlayerId = findTabletPlayerId(intent);
		Player myPlayer = findMyPlayer(myPlayerId, world);
		PlayerConfig config = new PlayerConfig(createMovementController(
				myPlayer, collision), findOtherPlayers(myPlayerId, world,
				collision), gameView, world);
		return config;
	}

	private static List<PlayerMovementController> findOtherPlayers(
			int myPlayerId, World world, CollisionDetection collisionDetection) {
		List<PlayerMovementController> list = new LinkedList<PlayerMovementController>();
		for (Player p : world.getAllPlayer()) {
			if (p.id() != myPlayerId) {
				list.add(createMovementController(p, collisionDetection));
			}
		}
		return list;
	}

	private static PlayerMovementController createMovementController(Player p,
			CollisionDetection collisionDetection) {
		return new PlayerMovementController(p, collisionDetection);
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
			return intent.getExtras().getInt(
					ActivityLauncher.PLAYER_ID_CONSTANT);
		} else {
			return 0;
		}
	}
}
