package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.graphics.AndroidDrawer;
import de.oetting.bumpingbunnies.communication.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.communication.MessageSenderToNetworkDelegate;
import de.oetting.bumpingbunnies.communication.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviver;
import de.oetting.bumpingbunnies.core.game.steps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.core.game.steps.factory.BunnyMovementStepFactory;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.input.UserInputStep;
import de.oetting.bumpingbunnies.core.input.factory.OpponentInputFactoryImpl;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.communication.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidObjectsDrawer;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(World world, Context context, Configuration configuration, CoordinatesCalculation calculations,
			CameraPositionCalculation cameraPositionCalculator, GameMain main, Player myPlayer, GameActivity activity, NetworkMessageDistributor sendControl) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, activity, world, networkDispatcher, sendControl);
		GameThreadState threadState = new GameThreadState();

		AndroidObjectsDrawer drawer = DrawerFactory.create(world, threadState, context, configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(world.getSpawnPoints());
		UserInputStep userInputStep = new UserInputStep(createInputServiceFactory(world, stateDispatcher));
		CollisionDetection colDetection = new CollisionDetection(world);
		PlayerReviver reviver = new PlayerReviver(new MessageSenderToNetworkDelegate(sendControl));
		BunnyKillChecker killChecker = createKillChecker(configuration, world, spawnPointGenerator, reviver, colDetection, sendControl);
		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, colDetection, world);
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(killChecker, factory);
		// Sending Coordinates Strep
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(new AndroidStateSenderFactory(sendControl, myPlayer));
		GameStepController worldController = new GameStepController(userInputStep, movementStep, sendCoordinates, reviver, cameraPositionCalculator);
		return createGameThread(configuration, threadState, drawer, worldController);
	}

	private static GameThread createGameThread(Configuration configuration, GameThreadState threadState, AndroidObjectsDrawer objectsDrawer,
			GameStepController worldController) {
		Drawer drawer = new AndroidDrawer(objectsDrawer, configuration.getLocalSettings().isAltPixelMode());
		return new GameThread(drawer, worldController, threadState);
	}

	private static OpponentInputFactory createInputServiceFactory(World world, PlayerStateDispatcher stateDispatcher) {
		return new OpponentInputFactoryImpl(world, stateDispatcher);
	}

	private static void initInputServices(GameMain main, GameActivity activity, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
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

	private static void createNetworkReceiveThreads(GameMain main, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		NetworkReceiveControl receiveControl = createNetworkReceiveControl(networkDispatcher, sendControl);
		main.setReceiveControl(receiveControl);
	}

	private static NetworkReceiveControl createNetworkReceiveControl(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context, CollisionDetection collisionDetection, World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, musicPlayer);
	}

	private static BunnyKillChecker createKillChecker(Configuration conf, World world, SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver,
			CollisionDetection collisionDetection, NetworkMessageDistributor sendControl) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(collisionDetection, world, spawnPointGenerator, reviver, new MessageSenderToNetworkDelegate(sendControl));
		} else {
			return new ClientBunnyKillChecker();
		}
	}

}
