package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.DefaultStateSenderFactory;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.StateSenderFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameThreadFactory {

	public GameThread create(World world, GameStopper gameStopper, Configuration configuration, CameraPositionCalculation cameraCalculation, Player myPlayer,
			NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		PlayerMovementCalculationFactory factory = createFactory(world);
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
