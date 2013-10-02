package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameObjectInteractor;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.PlayerReviver;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ResetToScorePoint;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(List<? extends RemoteSender> sendThreads, World world,
			List<OtherPlayerInputService> movementServices, List<StateSender> stateSender,
			Context context, AllPlayerConfig playerConfig,
			Configuration configuration, CoordinatesCalculation calculations, Player myPlayer) {
		GameThreadState threadState = new GameThreadState();

		Drawer drawer = DrawerFactory.create(world, threadState, context,
				configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		assignInitialSpawnpoints(spawnPointGenerator, world.getAllPlayer(), sendThreads);
		UserInputStep userInputStep = new UserInputStep(movementServices);
		CollisionDetection colDetection = new CollisionDetection(world);
		PlayerReviver reviver = createReviver(sendThreads, world.getAllPlayer(), configuration);
		BunnyKillChecker killChecker = createKillChecker(sendThreads, configuration, playerConfig.getAllPlayers(),
				spawnPointGenerator, reviver, colDetection);
		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, colDetection, world);
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(playerConfig.getAllPlayers(), killChecker, factory);
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(stateSender);
		GameStepController worldController = new GameStepController(
				userInputStep, movementStep, sendCoordinates, reviver);
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode(),
				createCameraPositionCalculator(myPlayer));
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context,
			CollisionDetection collisionDetection,
			World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection,
				world), collisionDetection, musicPlayer);
	}

	private static CameraPositionCalculation createCameraPositionCalculator(Player player) {
		return new CameraPositionCalculation(player);
	}

	private static void assignInitialSpawnpoints(SpawnPointGenerator spGenerator, List<Player> allPlayers,
			List<? extends RemoteSender> sendThreads) {
		for (Player p : allPlayers) {
			SpawnPoint nextSpawnPoint = spGenerator.nextSpawnPoint();
			ResetToScorePoint.resetPlayerToSpawnPoint(nextSpawnPoint, p);
			notifyAllClientsAboutSpawnpoints(sendThreads, nextSpawnPoint, p);
		}
	}

	private static void notifyAllClientsAboutSpawnpoints(List<? extends RemoteSender> sendThreads, SpawnPoint spawnpoint, Player player) {
		SpawnPointMessage message = new SpawnPointMessage(spawnpoint, player.id());
		for (RemoteSender sender : sendThreads) {
			new SpawnPointSender(sender).sendMessage(message);
		}
	}

	private static PlayerReviver createReviver(List<? extends RemoteSender> sendThreads, List<Player> list, Configuration configuration) {
		PlayerReviver reviver = new PlayerReviver(sendThreads);
		if (configuration.isHost()) {
			for (Player p : list) {
				reviver.revivePlayerLater(p);
			}
		} // all players are revived by a message from the host
		return reviver;
	}

	private static BunnyKillChecker createKillChecker(List<? extends RemoteSender> sendThreads, Configuration conf,
			List<Player> allPlayers,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, CollisionDetection collisionDetection) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(sendThreads, collisionDetection, allPlayers,
					spawnPointGenerator, reviver);
		} else {
			return new ClientBunnyKillChecker();
		}
	}

}
