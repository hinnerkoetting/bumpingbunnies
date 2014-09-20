package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.StateSenderFactory;
import de.oetting.bumpingbunnies.core.networking.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.pc.game.network.PcStateSenderFactory;
import de.oetting.bumpingbunnies.pc.graphics.PcDrawer;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameThreadFactory {

	public GameThread create(CoordinatesCalculation coordinatesCalculation, World world, GameStopper gameStopper, Configuration configuration, Player myPlayer) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		PlayerMovementCalculationFactory factory = createFactory(world);
		StateSenderFactory senderFactory = new PcStateSenderFactory();
		CameraPositionCalculation cameraCalculation = new CameraPositionCalculation(myPlayer);
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper, SocketStorage.getSingleton()));
		GameStepController stepController = GameStepControllerFactory.create(cameraCalculation, world, stateDispatcher, factory, senderFactory, sendControl,
				configuration);

		return new GameThread(new PcDrawer(), stepController, new GameThreadState());
	}

	private PlayerMovementCalculationFactory createFactory(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, new DummyMusicPlayer());
	}
}
