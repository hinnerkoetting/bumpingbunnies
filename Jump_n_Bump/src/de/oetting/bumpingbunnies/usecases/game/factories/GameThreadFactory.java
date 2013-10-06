package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameObjectInteractor;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkReceiveControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.PlayerReviver;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(World world,
			Context context,
			Configuration configuration, CoordinatesCalculation calculations,
			CameraPositionCalculation cameraPositionCalculator, GameMain main, Player myPlayer,
			GameActivity activity, NetworkSendControl sendControl) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, activity, world, networkDispatcher, sendControl);
		GameThreadState threadState = new GameThreadState();

		Drawer drawer = DrawerFactory.create(world, threadState, context,
				configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		UserInputStep userInputStep = new UserInputStep(createInputServiceFactory(world, stateDispatcher));
		CollisionDetection colDetection = new CollisionDetection(world);
		PlayerReviver reviver = new PlayerReviver(sendControl);
		BunnyKillChecker killChecker = createKillChecker(configuration, world,
				spawnPointGenerator, reviver, colDetection, sendControl);
		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, colDetection, world);
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(killChecker, factory);
		// Sending Coordinates Strep
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(new AndroidStateSenderFactory(sendControl, myPlayer));
		GameStepController worldController = new GameStepController(
				userInputStep, movementStep, sendCoordinates, reviver, cameraPositionCalculator);
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode());
	}

	private static OpponentInputFactory createInputServiceFactory(World world, PlayerStateDispatcher stateDispatcher) {
		return new OpponentInputFactoryImpl(world, stateDispatcher);
	}

	private static void initInputServices(GameMain main, GameActivity activity,
			World world, NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {
		addAllNetworkListeners(activity, networkDispatcher, world);
		createNetworkReceiveThreads(main, networkDispatcher, sendControl);
	}

	private static void addAllNetworkListeners(GameActivity activity, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
	}

	private static void createNetworkReceiveThreads(GameMain main,
			NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {
		NetworkReceiveControl receiveControl = createNetworkReceiveControl(networkDispatcher, sendControl);
		main.setReceiveControl(receiveControl);
	}

	private static NetworkReceiveControl createNetworkReceiveControl(NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher,
				sendControl);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context,
			CollisionDetection collisionDetection,
			World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection,
				world), collisionDetection, musicPlayer);
	}

	private static BunnyKillChecker createKillChecker(Configuration conf,
			World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, CollisionDetection collisionDetection,
			NetworkSendControl sendControl) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(collisionDetection, world,
					spawnPointGenerator, reviver, sendControl);
		} else {
			return new ClientBunnyKillChecker();
		}
	}

}
