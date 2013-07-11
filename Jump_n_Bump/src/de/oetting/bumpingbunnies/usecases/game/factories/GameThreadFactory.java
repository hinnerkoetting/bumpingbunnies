package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class GameThreadFactory {

	public static GameThread create(World world,

			List<InputService> movementServices, List<StateSender> stateSender,
			Context context, AllPlayerConfig playerConfig,
			Configuration configuration) {
		GameThreadState threadState = new GameThreadState();

		CoordinatesCalculation calculations = playerConfig.getCoordinateCalculations();
		Drawer drawer = DrawerFactory.create(world, threadState, context,
				playerConfig, configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		UserInputStep userInputStep = new UserInputStep(movementServices);
		List<PlayerMovementController> playermovements = playerConfig.getAllPlayerMovementControllers();
		BunnyMovementStep movementStep = new BunnyMovementStep(playermovements, spawnPointGenerator);
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(stateSender);
		GameStepController worldController = new GameStepController(
				userInputStep, movementStep, sendCoordinates);
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode());
	}
}
