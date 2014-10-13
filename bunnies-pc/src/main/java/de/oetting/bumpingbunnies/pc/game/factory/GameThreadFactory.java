package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.main.NetworkListeners;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameThreadFactory {

	public GameThread create(World world, ThreadErrorCallback gameStopper, Configuration configuration, CameraPositionCalculation cameraCalculation, Player myPlayer,
			NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, GameMain main) {
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, gameStopper, world, networkDispatcher, sendControl, configuration);

		PlayerMovementCalculationFactory factory = createFactory(world);
		GameStepController stepController = GameStepControllerFactory.create(cameraCalculation, world, stateDispatcher, factory, sendControl, configuration);

		return new GameThread(stepController);
	}

	private static void initInputServices(GameMain main, ThreadErrorCallback gameStopper, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, Configuration configuration) {
		NetworkListeners.allNetworkListeners(networkDispatcher, world, gameStopper, main, configuration);
	}

	private PlayerMovementCalculationFactory createFactory(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, new DummyMusicPlayer());
	}
}
