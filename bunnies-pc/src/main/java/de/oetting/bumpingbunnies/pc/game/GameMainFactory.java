package de.oetting.bumpingbunnies.pc.game;

import de.oetting.bumpingbunnies.core.configuration.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NoopNetworkReceiveFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, GameStartParameter parameter) {
		NoopGameStopper gameStopper = new NoopGameStopper();
		GameMain main = createGameMain(gameStopper, parameter, world);
		main.setGameThread(createGameThread(cameraPositionCalculator, world, gameStopper, parameter.getConfiguration()));
		main.setWorld(world);
		main.setReceiveControl(new NetworkReceiveControl(new NoopNetworkReceiveFactory()));
		main.validateInitialised();
		main.start();
		return main;
	}

	private GameMain createGameMain(NoopGameStopper gameStopper, GameStartParameter parameter, World world) {
		NetworkMessageDistributor networkMessageDistributor = new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper,
				SocketStorage.getSingleton()));
		NewClientsAccepter newClientsAccepter = createClientAccepter(parameter, world);
		return new GameMain(SocketStorage.getSingleton(), networkMessageDistributor, newClientsAccepter, new DummyMusicPlayer());
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world) {
		return NewClientsAccepterFactory.create(parameter, world, new PcConnectionEstablisherFactory());
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world, GameStopper gameStopper, Configuration configuration) {
		return new GameThreadFactory().create(world, gameStopper, configuration, cameraPositionCalculator);
	}
}
