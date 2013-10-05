package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.communication.MySocket;
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
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.BunnyMovementStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.PlayerReviver;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps.UserInputStep;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(List<? extends RemoteSender> sendThreads, World world,
			Context context,
			Configuration configuration, CoordinatesCalculation calculations,
			CameraPositionCalculation cameraPositionCalculator, GameMain main, Player myPlayer,
			GameActivity activity) {
		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		List<OpponentInput> inputServices = initInputServices(main, activity, world,
				main.getSendThreads(), networkDispatcher);
		GameThreadState threadState = new GameThreadState();

		Drawer drawer = DrawerFactory.create(world, threadState, context,
				configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		// assignSpawnPoints(sendThreads, myPlayer, extractOtherPlayers(otherPlayers), spawnPointGenerator);
		UserInputStep userInputStep = new UserInputStep(inputServices, createInputServiceFactory(main, world, stateDispatcher));
		CollisionDetection colDetection = new CollisionDetection(world);
		PlayerReviver reviver = new PlayerReviver(sendThreads);
		BunnyKillChecker killChecker = createKillChecker(sendThreads, configuration, world,
				spawnPointGenerator, reviver, colDetection);
		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, colDetection, world);
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(killChecker, factory);
		// Sending Coordinates Strep
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(new AndroidStateSenderFactory(main, myPlayer));
		GameStepController worldController = new GameStepController(
				userInputStep, movementStep, sendCoordinates, reviver, cameraPositionCalculator);
		return new GameThread(drawer, worldController, threadState, configuration.getLocalSettings().isAltPixelMode());
	}

	private static OpponentInputFactory createInputServiceFactory(GameMain main, World world,
			PlayerStateDispatcher stateDispatcher) {
		return new OpponentInputFactoryImpl(main, world, stateDispatcher);
	}

	private static List<OpponentInput> initInputServices(GameMain main, GameActivity activity,
			World world,
			List<? extends RemoteSender> allSender, NetworkToGameDispatcher networkDispatcher) {

		addAllNetworkListeners(activity, networkDispatcher, world);

		createNetworkReceiveThreads(main, networkDispatcher, allSender);
		List<OpponentInput> inputServices = createInputServicesForOtherPlayers();

		return inputServices;
	}

	private static void addAllNetworkListeners(GameActivity activity, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
	}

	private static void createNetworkReceiveThreads(GameMain main,
			NetworkToGameDispatcher networkDispatcher,
			List<? extends RemoteSender> allRemoteSender) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<NetworkReceiveThread> networkReceiveThreads = new ArrayList<NetworkReceiveThread>();
		for (MySocket socket : allSockets) {
			NetworkReceiveThread tcpReceiveThread = NetworkReceiverDispatcherThreadFactory
					.createGameNetworkReceiver(socket, allRemoteSender,
							networkDispatcher);
			NetworkReceiveThread udpReceiveThread = NetworkReceiverDispatcherThreadFactory
					.createGameNetworkReceiver(socket.createFastConnection(), allRemoteSender,
							networkDispatcher);

			networkReceiveThreads.add(tcpReceiveThread);
			networkReceiveThreads.add(udpReceiveThread);
		}
		NetworkReceiveControl receiveControl = createNetworkReceiveControl(main, networkDispatcher);
		main.setReceiveControl(receiveControl);
	}

	private static NetworkReceiveControl createNetworkReceiveControl(GameMain main, NetworkToGameDispatcher networkDispatcher) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), main, networkDispatcher);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}

	private static List<OpponentInput> createInputServicesForOtherPlayers() {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<OpponentInput> resultReceiver = new ArrayList<OpponentInput>(
				allSockets.size());

		return resultReceiver;
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context,
			CollisionDetection collisionDetection,
			World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection,
				world), collisionDetection, musicPlayer);
	}

	private static BunnyKillChecker createKillChecker(List<? extends RemoteSender> sendThreads, Configuration conf,
			World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, CollisionDetection collisionDetection) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(sendThreads, collisionDetection, world,
					spawnPointGenerator, reviver);
		} else {
			return new ClientBunnyKillChecker();
		}
	}

}
