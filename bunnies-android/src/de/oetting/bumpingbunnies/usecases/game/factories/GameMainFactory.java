package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidBitmapReader;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidResourceProvider;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlReader;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.CachedBitmapReader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity, GameStartParameter parameter, Player myPlayer, CameraPositionCalculation cameraCalclation) {
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(activity, SocketStorage.getSingleton()));

		World world = createWorld(activity, parameter);
		NewClientsAccepter clientAccepter = createClientAccepter(activity, parameter, world);
		GameMain main = new GameMain(SocketStorage.getSingleton(), sendControl, clientAccepter, MusicPlayerFactory.createBackground(activity));
		clientAccepter.setMain(main);

		initGame(main, activity, parameter, sendControl, world, myPlayer, cameraCalclation);

		List<PlayerConfig> otherPlayers = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());

		addPlayersToWorld(main, otherPlayers);
		main.start();
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

	private static void initGame(GameMain main, GameActivity activity, GameStartParameter parameter, NetworkMessageDistributor sendControl, World world,
			Player myPlayer, CameraPositionCalculation cameraPositionCalculation) {

		main.setWorld(world);

		GameThread gameThread = GameThreadFactory.create(world, activity, parameter.getConfiguration(), cameraPositionCalculation, main, myPlayer, activity,
				sendControl);
		main.setGameThread(gameThread);

		addJoinListener(main);
		main.newPlayerJoined(myPlayer);
	}

	private static void addPlayersToWorld(GameMain main, List<PlayerConfig> players) {

		for (PlayerConfig pc : players) {
			main.newPlayerJoined(pc.getPlayer());
		}
	}

}
