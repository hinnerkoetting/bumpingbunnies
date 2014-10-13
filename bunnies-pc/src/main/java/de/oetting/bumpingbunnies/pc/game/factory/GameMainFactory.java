package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.CommonGameMainFactory;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
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
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.pc.network.messaging.PcGameStopper;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, GameStartParameter parameter, Player myPlayer) {
		PcGameStopper gameStopper = new PcGameStopper();
		GameMain main = createGameMain(gameStopper, parameter, world);
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper, main));
		main.setSendControl(networkMessageDistributor);
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(main);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, gameStopper, parameter.getConfiguration(), myPlayer, networkDispatcher,
				networkMessageDistributor, main));
		main.setWorld(world);
		main.setReceiveControl(createNetworkReceiveFactory(networkDispatcher, networkMessageDistributor, parameter.getConfiguration()));
		main.validateInitialised();
		main.start();
		return main;
	}

	private NetworkReceiveControl createNetworkReceiveFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor networkMessageDistributor,
			Configuration configuration) {
		return NetworkReceiveControlFactory.create(networkDispatcher, networkMessageDistributor, configuration);
	}

	private GameMain createGameMain(ThreadErrorCallback gameStopper, GameStartParameter parameter, World world) {
		GameMain main = new GameMain(SocketStorage.getSingleton(), new DummyMusicPlayer());
		RemoteConnectionFactory connectionFactory = new RemoteConnectionFactory(gameStopper, main);
		NetworkPlayerStateSenderThread networkSendThread = NetworksendThreadFactory.create(world, connectionFactory);
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
			GameMain gameMain) {
		return new GameThreadFactory().create(world, gameStopper, configuration, cameraPositionCalculator, myPlayer, networkDispatcher, messageDistributor,
				gameMain);
	}
}
