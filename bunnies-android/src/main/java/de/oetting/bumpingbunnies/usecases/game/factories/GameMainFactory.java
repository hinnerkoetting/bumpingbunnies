package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidBitmapReader;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidResourceProvider;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlReader;
import de.oetting.bumpingbunnies.communication.AndroidConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.configuration.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkSendThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.network.factory.NetworksendThreadFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.CachedBitmapReader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity, GameStartParameter parameter, Player myPlayer, CameraPositionCalculation cameraCalclation) {
		RemoteConnectionFactory remoteConnectionFactory = new RemoteConnectionFactory(activity, SocketStorage.getSingleton());
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(remoteConnectionFactory);

		World world = createWorld(activity, parameter);
		NewClientsAccepter clientAccepter = createClientAccepter(parameter, world);

		NetworkSendThread networkSendThread = NetworksendThreadFactory.create(world, remoteConnectionFactory);

		GameMain main = new GameMain(SocketStorage.getSingleton(), sendControl, clientAccepter, MusicPlayerFactory.createBackground(activity),
				networkSendThread);
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

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world) {
		return NewClientsAccepterFactory.create(parameter, world, new AndroidConnectionEstablisherFactory());
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
