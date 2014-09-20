package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.game.GameView;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidBitmapReader;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidResourceProvider;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlReader;
import de.oetting.bumpingbunnies.communication.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.CachedBitmapReader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity, GameStartParameter parameter, Player myPlayer) {
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(activity, SocketStorage.getSingleton()));

		World world = createWorld(activity, parameter);
		NewClientsAccepter clientAccepter = createClientAccepter(activity, parameter, world);
		GameMain main = new GameMain(SocketStorage.getSingleton(), sendControl, clientAccepter);
		clientAccepter.setMain(main);

		GameThread gameThread = initGame(main, activity, parameter, sendControl, world, myPlayer);

		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);
		contentView.setGameThread(gameThread);
		List<PlayerConfig> otherPlayers = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());

		addPlayersToWorld(main, otherPlayers);
		gameThread.start();
		initGameSound(main, activity);
		return main;
	}

	private static World createWorld(GameActivity activity, GameStartParameter parameter) {
		WorldObjectsParser factory = new WorldConfigurationFactory().createWorldParser(parameter.getConfiguration().getWorldConfiguration());
		return factory.build(new AndroidResourceProvider(new CachedBitmapReader(new AndroidBitmapReader(activity.getResources())), activity),
				new AndroidXmlReader(activity, factory.getResourceId()));
	}

	private static NewClientsAccepter createClientAccepter(GameActivity activity, GameStartParameter parameter, World world) {
		return NewClientsAccepterFactory.create(parameter, activity, world);
	}

	private static void addJoinListener(GameMain main) {
		main.addAllJoinListeners();
	}

	private static GameThread initGame(GameMain main, GameActivity activity, GameStartParameter parameter, NetworkMessageDistributor sendControl, World world,
			Player myPlayer) {

		main.setWorld(world);
		final GameView contentView = (GameView) activity.findViewById(R.id.fullscreen_content);

		CameraPositionCalculation cameraPositionCalculation = createCameraPositionCalculator(myPlayer);
		RelativeCoordinatesCalculation calculations = createCoordinatesCalculation(cameraPositionCalculation);

		GameThread gameThread = GameThreadFactory.create(world, activity, parameter.getConfiguration(), calculations, cameraPositionCalculation, main,
				myPlayer, activity, sendControl);
		main.setGameThread(gameThread);

		contentView.addOnSizeListener(gameThread);

		addJoinListener(main);
		main.newPlayerJoined(myPlayer);
		return gameThread;
	}

	private static void addPlayersToWorld(GameMain main, List<PlayerConfig> players) {

		for (PlayerConfig pc : players) {
			main.newPlayerJoined(pc.getPlayer());
		}
	}

	public static InputDispatcher<?> createInputDispatcher(GameActivity activity, GameStartParameter parameter, Player myPlayer) {
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = new InputConfigurationFactory().create(parameter.getConfiguration()
				.getInputConfiguration());
		InputService touchService = myPlayerFactory.createInputService(new PlayerMovement(myPlayer), activity, createCoordinatesCalculation(myPlayer));
		InputDispatcher<?> inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		myPlayerFactory.insertGameControllerViews((ViewGroup) activity.findViewById(R.id.game_root), activity.getLayoutInflater(), inputDispatcher);
		return inputDispatcher;
	}

	private static RelativeCoordinatesCalculation createCoordinatesCalculation(Player player) {
		return createCoordinatesCalculation(createCameraPositionCalculator(player));
	}

	private static RelativeCoordinatesCalculation createCoordinatesCalculation(CameraPositionCalculation cameraPositionCalculation) {
		return new RelativeCoordinatesCalculation(cameraPositionCalculation);
	}

	private static CameraPositionCalculation createCameraPositionCalculator(Player player) {
		return new CameraPositionCalculation(player);
	}

	private static void initGameSound(GameMain main, GameActivity activity) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createBackground(activity);
		main.setMusicPlayer(musicPlayer);
	}
}
