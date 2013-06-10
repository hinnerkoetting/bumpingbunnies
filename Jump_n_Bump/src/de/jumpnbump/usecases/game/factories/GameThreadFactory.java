package de.jumpnbump.usecases.game.factories;

import java.util.List;

import android.content.res.Resources;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.businesslogic.WorldController;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.World;

public class GameThreadFactory {

	public static GameThread create(World world,
			GameThreadState gameThreadState,
			List<PlayerMovementController> playermovements,
			List<InputService> movementServices, List<StateSender> stateSender,
			Resources resources, PlayerConfig playerConfig,
			Configuration configuration) {
		Drawer drawer = DrawerFactory.create(world, gameThreadState, resources,
				playerConfig, configuration);
		WorldController worldController = new WorldController(playermovements,
				movementServices, stateSender);
		return new GameThread(drawer, worldController, gameThreadState);
	}
}
