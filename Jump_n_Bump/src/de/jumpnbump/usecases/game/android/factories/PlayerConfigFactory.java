package de.jumpnbump.usecases.game.android.factories;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.android.PlayerConfig;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfigFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(PlayerConfigFactory.class);

	public static PlayerConfig create(Intent intent, World world) {
		Player myPlayer = findMyPlayer(intent, world);
		PlayerConfig config = new PlayerConfig(myPlayer, findOtherPlayers(
				intent, world));
		return config;
	}

	private static List<Player> findOtherPlayers(Intent intent, World world) {
		int myPlayerId = findTabletPlayerId(intent);
		if (myPlayerId == 0) {
			return Arrays.asList(world.getPlayer2());
		} else {
			return Arrays.asList(world.getPlayer1());
		}
	}

	private static Player findMyPlayer(Intent intent, World world) {
		int myPlayerId = findTabletPlayerId(intent);
		LOGGER.info("My player has id %d", myPlayerId);
		if (myPlayerId == 0) {
			return world.getPlayer1();
		} else {
			return world.getPlayer2();
		}
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
