package de.oetting.bumpingbunnies.core.game;

import java.util.List;

import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.configuration.PlayerConfigFactory;
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
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.PlayerConfig;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameMainFactory {

	public static GameMain create(GameStartParameter parameter, Player myPlayer, CameraPositionCalculation cameraCalclation, ThreadErrorCallback errorCallback,
			BunniesMusicPlayerFactory musicPlayerFactory, World world, ConnectionEstablisherFactory connectionEstablisherFactory) {
		GameMain main = new GameMain(SocketStorage.getSingleton(), musicPlayerFactory.createBackground());

		RemoteConnectionFactory remoteConnectionFactory = new RemoteConnectionFactory(errorCallback, main);
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(remoteConnectionFactory);
		NetworkPlayerStateSenderThread networkSendThread = NetworksendThreadFactory.create(world, remoteConnectionFactory, errorCallback);
		NewClientsAccepter clientAccepter = createClientAccepter(parameter, world, main, errorCallback, connectionEstablisherFactory);
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

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, PlayerDisconnectedCallback callback,
			ThreadErrorCallback errorCallback, ConnectionEstablisherFactory connectionEstablisherFactory) {
		return CommonGameMainFactory.createClientAccepter(parameter, world, connectionEstablisherFactory, callback, errorCallback);
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
