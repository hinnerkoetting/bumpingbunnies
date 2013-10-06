package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
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
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity) {
		NetworkSendControl sendControl = new NetworkSendControl(new RemoteConnectionFactory(activity,
				SocketStorage.getSingleton()));
		GameStartParameter parameter = (GameStartParameter) activity.getIntent()
				.getExtras().get(ActivityLauncher.GAMEPARAMETER);
		GameMain main = new GameMain(SocketStorage.getSingleton(), sendControl, NewClientsAccepterFactory.create(parameter, activity));
		GameThread gameThread = initGame(main, activity, parameter, sendControl);

		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		contentView.setGameThread(gameThread);
		List<PlayerConfig> otherPlayers = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());
		addPlayersToWorld(main, otherPlayers);
		gameThread.start();
		initGameSound(main, activity);

		return main;
	}

	private static void addJoinListener(GameMain main) {
		main.addAllJoinListeners();
	}

	private static GameThread initGame(GameMain main, GameActivity activity, GameStartParameter parameter, NetworkSendControl sendControl) {

		World world = WorldFactory.create(parameter.getConfiguration(), activity);
		main.setWorld(world);
		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		Player myPlayer = PlayerConfigFactory.createMyPlayer(parameter);

		CameraPositionCalculation cameraPositionCalculation = createCameraPositionCalculator(myPlayer);
		RelativeCoordinatesCalculation calculations = new RelativeCoordinatesCalculation(cameraPositionCalculation);

		GameThread gameThread = GameThreadFactory.create(world,
				activity, parameter.getConfiguration(), calculations, cameraPositionCalculation, main, myPlayer,
				activity, sendControl);
		main.setGameThread(gameThread);

		contentView.addOnSizeListener(gameThread);

		main.setInputDispatcher(createInputDispatcher(activity, parameter, calculations, myPlayer));
		addJoinListener(main);

		main.playerJoins(myPlayer);
		return gameThread;
	}

	private static CameraPositionCalculation createCameraPositionCalculator(Player player) {
		return new CameraPositionCalculation(player);
	}

	private static void addPlayersToWorld(GameMain main, List<PlayerConfig> players) {

		for (PlayerConfig pc : players) {
			main.playerJoins(pc.getPlayer());
		}
	}

	@SuppressWarnings("unchecked")
	private static InputDispatcher<?> createInputDispatcher(GameActivity activity,
			GameStartParameter parameter, CoordinatesCalculation calculations, Player myPlayer) {
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = (AbstractPlayerInputServicesFactory<InputService>) parameter
				.getConfiguration().getInputConfiguration()
				.createInputconfigurationClass();
		InputService touchService = myPlayerFactory.createInputService(new PlayerMovement(myPlayer), activity, calculations);
		InputDispatcher<?> inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		myPlayerFactory.insertGameControllerViews(
				(ViewGroup) activity.findViewById(R.id.game_root), activity.getLayoutInflater(),
				inputDispatcher);
		return inputDispatcher;
	}

	private static void initGameSound(GameMain main, GameActivity activity) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createBackground(activity);
		main.setMusicPlayer(musicPlayer);
	}
}
