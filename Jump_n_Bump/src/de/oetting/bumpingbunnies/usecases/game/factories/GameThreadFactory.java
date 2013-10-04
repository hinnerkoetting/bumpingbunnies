package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.factories.businessLogic.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameObjectInteractor;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStepController;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
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
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameReceiver;
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
			Context context,
			Configuration configuration, CoordinatesCalculation calculations,
			CameraPositionCalculation cameraPositionCalculator, GameMain main, Player myPlayer, List<PlayerConfig> otherPlayers,
			GameActivity activity) {
		List<OtherPlayerInputService> inputServices = initInputServices(main, activity, world,
				main.getSendThreads(), otherPlayers);
		GameThreadState threadState = new GameThreadState();

		Drawer drawer = DrawerFactory.create(world, threadState, context,
				configuration, calculations);
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(
				world.getSpawnPoints());
		assignSpawnPoints(sendThreads, myPlayer, extractOtherPlayers(otherPlayers), spawnPointGenerator);
		UserInputStep userInputStep = new UserInputStep(inputServices, null);
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

	private static List<Player> extractOtherPlayers(List<PlayerConfig> otherPlayers) {
		List<Player> players = new ArrayList<Player>(otherPlayers.size());
		for (PlayerConfig pc : otherPlayers) {
			players.add(pc.getMovementController().getPlayer());
		}
		return players;
	}

	private static List<OtherPlayerInputService> initInputServices(GameMain main, GameActivity activity,
			World world,
			List<? extends RemoteSender> allSender, List<PlayerConfig> otherPlayers) {

		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();

		addAllNetworkListeners(activity, networkDispatcher, world);

		createNetworkReceiveThreads(main, networkDispatcher, allSender);
		List<OtherPlayerInputService> inputServices = createInputServicesForOtherPlayers(otherPlayers, networkDispatcher);

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
		main.setNetworkReceiveThreads(networkReceiveThreads);
	}

	private static List<OtherPlayerInputService> createInputServicesForOtherPlayers(List<PlayerConfig> otherPlayers,
			NetworkToGameDispatcher networkDispatcher) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<OtherPlayerInputService> resultReceiver = new ArrayList<OtherPlayerInputService>(
				allSockets.size());

		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		for (PlayerConfig config : otherPlayers) {
			OtherPlayerInputService is = config.createInputService();
			if (is instanceof PlayerFromNetworkInput) {
				PlayerFromNetworkInput inputservice = (PlayerFromNetworkInput) is;
				stateDispatcher.addInputService(config.getMovementController()
						.getPlayer().id(), inputservice);
			}

			resultReceiver.add(is);
		}
		return resultReceiver;
	}

	private static void assignSpawnPoints(List<? extends RemoteSender> sendThreads, Player myPlayer, List<Player> otherPlayers,
			SpawnPointGenerator spawnPointGenerator) {
		assignInitialSpawnpoints(spawnPointGenerator, otherPlayers, sendThreads);
		SpawnPoint nextSpawnPoint = spawnPointGenerator.nextSpawnPoint();
		ResetToScorePoint.resetPlayerToSpawnPoint(nextSpawnPoint, myPlayer);
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context,
			CollisionDetection collisionDetection,
			World world) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection,
				world), collisionDetection, musicPlayer);
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
