package de.oetting.bumpingbunnies.pc.game;

import de.oetting.bumpingbunnies.core.configuration.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, GameStartParameter parameter, Player myPlayer) {
		NoopGameStopper gameStopper = new NoopGameStopper();
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper,
				SocketStorage.getSingleton()));
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		GameMain main = createGameMain(gameStopper, parameter, world, networkMessageDistributor);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, gameStopper, parameter.getConfiguration(), myPlayer));
		main.setWorld(world);
		main.setReceiveControl(createNetworkReceiveFactory(networkDispatcher, networkMessageDistributor));
		main.validateInitialised();
		main.start();
		return main;
	}

	private NetworkReceiveControl createNetworkReceiveFactory(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor networkMessageDistributor) {
		return NetworkReceiveControlFactory.create(networkDispatcher, networkMessageDistributor, new PcOpponentReceiverFactoryFactory());
	}

	private GameMain createGameMain(NoopGameStopper gameStopper, GameStartParameter parameter, World world, NetworkMessageDistributor networkMessageDistributor) {
		NewClientsAccepter newClientsAccepter = createClientAccepter(parameter, world);
		GameMain main = new GameMain(SocketStorage.getSingleton(), networkMessageDistributor, newClientsAccepter, new DummyMusicPlayer());
		newClientsAccepter.setMain(main);
		return main;
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world) {
		return NewClientsAccepterFactory.create(parameter, world, new PcConnectionEstablisherFactory());
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world, GameStopper gameStopper, Configuration configuration,
			Player myPlayer) {
		return new GameThreadFactory().create(world, gameStopper, configuration, cameraPositionCalculator, myPlayer);
	}
}
