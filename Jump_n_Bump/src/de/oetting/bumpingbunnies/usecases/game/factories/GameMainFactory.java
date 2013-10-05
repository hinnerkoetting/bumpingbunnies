package de.oetting.bumpingbunnies.usecases.game.factories;

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
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameThread;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity) {
		GameMain main = new GameMain(activity, SocketStorage.getSingleton());
		initGame(main, activity);

		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		contentView.setGameThread(main.getGameThread());

		startNetworkThreads(main);
		main.getGameThread().start();
		initGameSound(main, activity);

		return main;
	}

	private static void addJoinListener(GameMain main) {
		main.addAllJoinListeners();
	}

	private static void initGame(GameMain main, GameActivity activity) {
		GameStartParameter parameter = (GameStartParameter) activity.getIntent()
				.getExtras().get(ActivityLauncher.GAMEPARAMETER);
		World world = WorldFactory.create(parameter.getConfiguration(), activity);
		main.setWorld(world);
		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		PlayerMovement myPlayerMovement = PlayerConfigFactory.createMyPlayer(parameter);
		Player myPlayer = myPlayerMovement.getPlayer();
		List<PlayerConfig> otherPlayers = PlayerConfigFactory.findOtherPlayers(parameter.getConfiguration());

		CameraPositionCalculation cameraPositionCalculation = createCameraPositionCalculator(myPlayer);
		RelativeCoordinatesCalculation calculations = new RelativeCoordinatesCalculation(cameraPositionCalculation);

		GameThread gameThread = GameThreadFactory.create(main.getSendThreads(), world,
				activity, parameter.getConfiguration(), calculations, cameraPositionCalculation, main, myPlayer,
				otherPlayers, activity);
		main.setGameThread(gameThread);

		contentView.addOnSizeListener(gameThread);

		main.setInputDispatcher(createInputDispatcher(activity, parameter, calculations, myPlayerMovement));
		addJoinListener(main);
		addPlayersToWorld(main, myPlayer, otherPlayers);
	}

	private static CameraPositionCalculation createCameraPositionCalculator(Player player) {
		return new CameraPositionCalculation(player);
	}

	private static void addPlayersToWorld(GameMain main, Player myPlayer, List<PlayerConfig> players) {
		main.playerJoins(myPlayer);
		for (PlayerConfig pc : players) {
			main.playerJoins(pc.getMovementController().getPlayer());
		}
	}

	public static RemoteConnection createServerConnection(GameActivity activity, MySocket socket, Opponent opponent) {
		NetworkSendQueueThread tcpConnection = NetworkSendQueueThreadFactory.create(socket, activity);
		NetworkSendQueueThread udpConnection = createUdpConnection(activity, socket);

		RemoteConnection serverConnection = new RemoteConnection(tcpConnection, udpConnection, opponent);
		return serverConnection;
	}

	private static NetworkSendQueueThread createUdpConnection(GameActivity activity, MySocket socket) {
		MySocket fastSocket = socket.createFastConnection();
		return NetworkSendQueueThreadFactory.create(fastSocket, activity);
	}

	@SuppressWarnings("unchecked")
	private static InputDispatcher<?> createInputDispatcher(GameActivity activity,
			GameStartParameter parameter, CoordinatesCalculation calculations, PlayerMovement myPlayerMovement) {
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = (AbstractPlayerInputServicesFactory<InputService>) parameter
				.getConfiguration().getInputConfiguration()
				.createInputconfigurationClass();
		InputService touchService = myPlayerFactory.createInputService(myPlayerMovement, activity, calculations);
		InputDispatcher<?> inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) activity.findViewById(R.id.game_root), activity.getLayoutInflater(),
				inputDispatcher);
		return inputDispatcher;
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
