package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.networking.DefaultStateSenderFactory;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.StateSenderFactory;
import de.oetting.bumpingbunnies.core.networking.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameThreadFactory {

	public GameThread create(World world, GameStopper gameStopper, Configuration configuration, CameraPositionCalculation cameraCalculation, Player myPlayer) {
		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		PlayerMovementCalculationFactory factory = createFactory(world);
		NetworkMessageDistributor sendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(gameStopper, SocketStorage.getSingleton()));
		StateSenderFactory senderFactory = new DefaultStateSenderFactory(sendControl, myPlayer);
		GameStepController stepController = GameStepControllerFactory.create(cameraCalculation, world, stateDispatcher, factory, senderFactory, sendControl,
				configuration);

		return new GameThread(stepController);
	}

	private PlayerMovementCalculationFactory createFactory(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, new DummyMusicPlayer());
	}
}
