package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviver;
import de.oetting.bumpingbunnies.core.input.UserInputStep;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.MessageSenderToNetworkDelegate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkReceiveControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.communication.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(World world, Context context, Configuration configuration, CoordinatesCalculation calculations,
			CameraPositionCalculation cameraPositionCalculator, GameMain main, Player myPlayer, GameActivity activity, NetworkSendControl sendControl) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, activity, world, networkDispatcher, sendControl);
		GameThreadState threadState = new GameThreadState();

		Drawer drawer = DrawerFactory.create(world, threadState, context, configuration, calculations);
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
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode());
	}

	private static OpponentInputFactory createInputServiceFactory(World world, PlayerStateDispatcher stateDispatcher) {
		return new OpponentInputFactoryImpl(world, stateDispatcher);
	}

	private static void initInputServices(GameMain main, GameActivity activity, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkSendControl sendControl) {
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

	private static void createNetworkReceiveThreads(GameMain main, NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {
		NetworkReceiveControl receiveControl = createNetworkReceiveControl(networkDispatcher, sendControl);
		main.setReceiveControl(receiveControl);
	}

	private static NetworkReceiveControl createNetworkReceiveControl(NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context, CollisionDetection collisionDetection, World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, musicPlayer);
	}

	private static BunnyKillChecker createKillChecker(Configuration conf, World world, SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver,
			CollisionDetection collisionDetection, NetworkSendControl sendControl) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(collisionDetection, world, spawnPointGenerator, reviver, new MessageSenderToNetworkDelegate(sendControl));
		} else {
			return new ClientBunnyKillChecker();
		}
	}

}
