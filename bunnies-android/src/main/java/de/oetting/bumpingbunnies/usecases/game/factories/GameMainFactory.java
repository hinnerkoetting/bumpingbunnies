package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidBitmapReader;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidResourceProvider;
import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlReader;
import de.oetting.bumpingbunnies.communication.AndroidConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.CommonGameMainFactory;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.factory.NetworksendThreadFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.CachedBitmapReader;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationFactory;

public class GameMainFactory {

	public static GameMain create(GameActivity activity, GameStartParameter parameter, Player myPlayer, CameraPositionCalculation cameraCalclation,
			ThreadErrorCallback errorCallback, BunniesMusicPlayerFactory musicPlayerFactory) {
		GameMain main = new GameMain(SocketStorage.getSingleton(), musicPlayerFactory.createBackground());
		World world = createWorld(activity, parameter);

		RemoteConnectionFactory remoteConnectionFactory = new RemoteConnectionFactory(activity, main);
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(remoteConnectionFactory);
		NetworkPlayerStateSenderThread networkSendThread = NetworksendThreadFactory.create(world, remoteConnectionFactory, activity);
		NewClientsAccepter clientAccepter = createClientAccepter(parameter, world, main, activity);
		clientAccepter.setMain(main);
		main.setNetworkSendThread(networkSendThread);
		main.setSendControl(sendControl);
		main.setNewClientsAccepter(clientAccepter);

		initGame(main, musicPlayerFactory.createJumper(), parameter, sendControl, world, myPlayer, cameraCalclation, errorCallback);

		List<PlayerConfig> otherPlayers = PlayerConfigFactory.createOtherPlayers(parameter.getConfiguration());

		addPlayersToWorld(main, otherPlayers);
		main.validateInitialised();

		main.start();
		return main;
	}

	private static World createWorld(GameActivity activity, GameStartParameter parameter) {
		WorldObjectsParser factory = new WorldConfigurationFactory().createWorldParser(parameter.getConfiguration().getWorldConfiguration());
		return factory.build(new AndroidResourceProvider(new CachedBitmapReader(new AndroidBitmapReader()), activity),
				new AndroidXmlReader(activity, factory.getResourceId()));
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, PlayerDisconnectedCallback callback,
			ThreadErrorCallback errorCallback) {
		return CommonGameMainFactory.createClientAccepter(parameter, world, new AndroidConnectionEstablisherFactory(), callback, errorCallback);
	}

	private static void addListener(GameMain main) {
		main.addAllJoinListeners();
		main.addSocketListener();
	}

	private static void initGame(GameMain main, MusicPlayer jumpMusicPlayer, GameStartParameter parameter, NetworkMessageDistributor sendControl, World world,
			Player myPlayer, CameraPositionCalculation cameraPositionCalculation, ThreadErrorCallback errorCallback) {

		main.setWorld(world);

		GameThread gameThread = GameThreadFactory.create(world, jumpMusicPlayer, parameter.getConfiguration(), cameraPositionCalculation, main, myPlayer,
				sendControl, main, errorCallback);
		main.setGameThread(gameThread);

		addListener(main);
		main.newEvent(myPlayer);
	}

	private static void addPlayersToWorld(GameMain main, List<PlayerConfig> players) {

		for (PlayerConfig pc : players) {
			main.newEvent(pc.getPlayer());
		}
	}

}
