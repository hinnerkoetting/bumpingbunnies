package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.factories.PlayerConfigFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity) {
		GameMain main = new GameMain();
		initGame(main, activity);

		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		contentView.setGameThread(main.getGameThread());

		startNetworkThreads(main);
		main.getGameThread().start();
		initGameSound(main, activity);
		return main;
	}

	private static void initGame(GameMain main, GameActivity activity) {
		GameStartParameter parameter = (GameStartParameter) activity.getIntent()
				.getExtras().get(ActivityLauncher.GAMEPARAMETER);
		World world = WorldFactory.create(parameter.getConfiguration(), activity);
		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		AllPlayerConfig allPlayerConfig = PlayerConfigFactory.create(parameter, world);
		RelativeCoordinatesCalculation calculations = new RelativeCoordinatesCalculation(allPlayerConfig.getMyPlayer());
		Player myPlayer = allPlayerConfig.getMyPlayer();
		createRemoteSender(main, activity);
		List<StateSender> allStateSender = createSender(main, myPlayer);
		List<OtherPlayerInputService> inputServices = initInputServices(main, activity, world,
				allPlayerConfig,
				main.getSendThreads());

		GameThread gameThread = GameThreadFactory.create(main.getSendThreads(), world,
				inputServices,
				allStateSender, activity, allPlayerConfig, parameter.getConfiguration(), calculations);
		main.setGameThread(gameThread);

		contentView.addOnSizeListener(gameThread);

		main.setAllPlayerConfig(allPlayerConfig);

		main.setInputDispatcher(createInputDispatcher(activity, allPlayerConfig, parameter, calculations));
	}

	private static void createRemoteSender(GameMain main, GameActivity activity) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<RemoteConnection> resultSender = new ArrayList<RemoteConnection>(
				allSockets.size());
		for (MySocket socket : allSockets) {
			RemoteConnection serverConnection = createServerConnection(activity, socket);
			resultSender.add(serverConnection);
		}
		main.setSendThreads(resultSender);
	}

	public static RemoteConnection createServerConnection(GameActivity activity, MySocket socket) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, activity);
		NetworkSendQueueThread udpConnection = createUdpConnection(activity, socket);

		RemoteConnection serverConnection = new RemoteConnection(tcpConnection, udpConnection);
		return serverConnection;
	}

	private static NetworkSendQueueThread createUdpConnection(GameActivity activity, MySocket socket) {
		MySocket fastSocket = socket.createFastConnection();
		return NetworkSendQueueThreadFactory.create(fastSocket, activity);
	}

	private static List<StateSender> createSender(GameMain main, Player myPlayer) {
		List<StateSender> resultSender = new ArrayList<StateSender>(
				main.getSendThreads().size());
		for (RemoteConnection rs : main.getSendThreads()) {
			resultSender.add(new GameNetworkSender(myPlayer, rs));
		}
		return resultSender;
	}

	private static List<OtherPlayerInputService> initInputServices(GameMain main, GameActivity activity,
			World world, AllPlayerConfig config,
			List<? extends RemoteSender> allSender) {

		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();

		addAllNetworkListeners(activity, networkDispatcher, world);

		createNetworkReceiveThreads(main, networkDispatcher, allSender);
		List<OtherPlayerInputService> inputServices = config.createOtherInputService(networkDispatcher);

		return inputServices;
	}

	private static InputDispatcher<?> createInputDispatcher(GameActivity activity, AllPlayerConfig config,
			GameStartParameter parameter, CoordinatesCalculation calculations) {
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = (AbstractPlayerInputServicesFactory<InputService>) parameter
				.getConfiguration().getInputConfiguration()
				.createInputconfigurationClass();
		InputService touchService = myPlayerFactory.createInputService(config.getTabletControlledPlayerMovement(), activity, calculations);
		InputDispatcher<?> inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) activity.findViewById(R.id.game_root), activity.getLayoutInflater(),
				inputDispatcher);
		return inputDispatcher;
	}

	private static void addAllNetworkListeners(GameActivity activity, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world.getAllPlayer());
		new PlayerScoreReceiver(networkDispatcher, world.getAllPlayer());
		new PlayerIsRevivedReceiver(networkDispatcher, world.getAllPlayer());
		new SpawnPointReceiver(networkDispatcher, world.getAllPlayer());
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

	private static void startNetworkThreads(GameMain main) {
		for (NetworkReceiveThread receiver : main.getNetworkReceiveThreads()) {
			receiver.start();
		}
		for (RemoteSender sender : main.getSendThreads()) {
			sender.start();
		}
	}

	private static void initGameSound(GameMain main, GameActivity activity) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createBackground(activity);
		main.setMusicPlayer(musicPlayer);
	}
}
