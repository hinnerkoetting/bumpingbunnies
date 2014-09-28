package de.oetting.bumpingbunnies.pc.game;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.networking.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.NoopGameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NoopNetworkReceiveFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.pc.game.factory.GameThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

public class GameMainFactory {

	public GameMain create(CameraPositionCalculation cameraPositionCalculator, World world, Configuration configuration) {
		NoopGameStopper gameStopper = new NoopGameStopper();
		GameMain main = new GameMain(SocketStorage.getSingleton(), new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper,
				SocketStorage.getSingleton())), new DummyNewClientsAccepter(), new DummyMusicPlayer());
		main.setGameThread(createGameThread(cameraPositionCalculator, world, gameStopper, configuration));
		main.setWorld(world);
		main.setReceiveControl(new NetworkReceiveControl(new NoopNetworkReceiveFactory()));
		main.validateInitialised();
		main.start();
		return main;
	}

	private GameThread createGameThread(CameraPositionCalculation cameraPositionCalculator, World world, GameStopper gameStopper, Configuration configuration) {
		return new GameThreadFactory().create(world, gameStopper, configuration, cameraPositionCalculator);
	}
}
