package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class GameThreadFactory {

	public static GameThread create(List<RemoteSender> sendThreads, World world,
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
		BunnyKillChecker killChecker = createKillChecker(sendThreads, configuration, world, extractPlayers(playermovements),
				spawnPointGenerator);
		BunnyMovementStep movementStep = new BunnyMovementStep(playermovements, killChecker);
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(stateSender);
		GameStepController worldController = new GameStepController(
				userInputStep, movementStep, sendCoordinates);
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode());
	}

	private static BunnyKillChecker createKillChecker(List<RemoteSender> sendThreads, Configuration conf, World world,
			List<Player> allPlayers,
			SpawnPointGenerator spawnPointGenerator) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		if (conf.isHost()) {
			return new HostBunnyKillChecker(sendThreads, collisionDetection, allPlayers,
					spawnPointGenerator);
		} else {
			return new ClientBunnyKillChecker();
		}
	}

	private static List<Player> extractPlayers(List<PlayerMovementController> movement) {
		List<Player> players = new ArrayList<Player>(movement.size());
		for (PlayerMovementController m : movement) {
			players.add(m.getPlayer());
		}
		return players;
	}
}
