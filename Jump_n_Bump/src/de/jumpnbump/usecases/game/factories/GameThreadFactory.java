package de.jumpnbump.usecases.game.factories;

import java.util.List;

import de.jumpnbump.usecases.game.GameThread;
import de.jumpnbump.usecases.game.GameThreadState;
import de.jumpnbump.usecases.game.WorldController;
import de.jumpnbump.usecases.game.businesslogic.InputService;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.World;

public class GameThreadFactory {

	public static GameThread create(World world,
			GameThreadState gameThreadState,
			List<GamePlayerController> playermovements,
			List<InputService> movementServices) {
		Drawer drawer = DrawerFactory.create(world, gameThreadState);
		WorldController worldController = new WorldController(playermovements,
				movementServices);
		return new GameThread(drawer, worldController, gameThreadState);
	}
}
