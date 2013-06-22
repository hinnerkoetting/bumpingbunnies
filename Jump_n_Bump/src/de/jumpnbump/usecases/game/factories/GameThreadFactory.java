package de.jumpnbump.usecases.game.factories;

import java.util.List;

import android.content.Context;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.GameThread;
import de.jumpnbump.usecases.game.businesslogic.ListSpawnPointGenerator;
import de.jumpnbump.usecases.game.businesslogic.AllPlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.businesslogic.SpawnPointGenerator;
import de.jumpnbump.usecases.game.businesslogic.WorldController;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.World;

public class GameThreadFactory {

	public static GameThread create(World world,
			List<PlayerMovementController> playermovements,
			List<InputService> movementServices, List<StateSender> stateSender,
			Context context, AllPlayerConfig playerConfig,
			Configuration configuration, CoordinatesCalculation calculations) {
		GameThreadState threadState = new GameThreadState();
		Drawer drawer = DrawerFactory.create(world, threadState, context,
				playerConfig, configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		WorldController worldController = new WorldController(playermovements,
				movementServices, stateSender, spawnPointGenerator);
		return new GameThread(drawer, worldController, threadState);
	}
}
