package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.logic.CommonGameThreadFactory;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.CommonGameMainFactory;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.factory.NetworksendThreadFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, GameStartParameter parameter, Player myPlayer,
			ThreadErrorCallback errorCallback, BunniesMusicPlayerFactory musicPlayerFactory) {

		GameMain main = createGameMain(errorCallback, parameter, world, musicPlayerFactory);
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(new RemoteConnectionFactory(errorCallback, main));
		main.setSendControl(networkMessageDistributor);
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(main);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, errorCallback, parameter.getConfiguration(), myPlayer, networkDispatcher,
				networkMessageDistributor, main, musicPlayerFactory));
		main.setWorld(world);
		main.setReceiveControl(createNetworkReceiveFactory(networkDispatcher, networkMessageDistributor, parameter.getConfiguration(), errorCallback, world));
		main.validateInitialised();
		main.start();
		main.onResume();
		return main;
	}

	private NetworkReceiveControl createNetworkReceiveFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor networkMessageDistributor,
			Configuration configuration, ThreadErrorCallback errorCallback, World world) {
		return NetworkReceiveControlFactory.create(networkDispatcher, networkMessageDistributor, configuration, errorCallback, world);
	}

	private GameMain createGameMain(ThreadErrorCallback gameStopper, GameStartParameter parameter, World world, BunniesMusicPlayerFactory musicPlayerFactory) {
		GameMain main = new GameMain(SocketStorage.getSingleton(), musicPlayerFactory.createBackground());
		RemoteConnectionFactory connectionFactory = new RemoteConnectionFactory(gameStopper, main);
		NetworkPlayerStateSenderThread networkSendThread = NetworksendThreadFactory.create(world, connectionFactory, gameStopper);
		main.setNetworkSendThread(networkSendThread);
		NewClientsAccepter newClientsAccepter = createClientAccepter(parameter, world, main, gameStopper);
		newClientsAccepter.setMain(main);
		main.setNewClientsAccepter(newClientsAccepter);
		return main;
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, PlayerDisconnectedCallback callback,
			ThreadErrorCallback errorCallback) {
		return CommonGameMainFactory.createClientAccepter(parameter, world, new PcConnectionEstablisherFactory(), callback, errorCallback);
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world, ThreadErrorCallback gameStopper,
			Configuration configuration, Player myPlayer, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor messageDistributor,
			GameMain gameMain, BunniesMusicPlayerFactory musicPlayerFactory) {
		return CommonGameThreadFactory.create(world, gameStopper, configuration, cameraPositionCalculator, myPlayer, networkDispatcher, messageDistributor,
				gameMain, musicPlayerFactory.createJumper());
	}
}
