package de.oetting.bumpingbunnies.core.game.logic;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.NetworkListeners;
import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Unified creation of gamethread belongs here.
 *
 */
public class CommonGameThreadFactory {

	private static final int MAX_GAME_LOOPS_PER_SECOND = 100;

	public static GameThread create(World world, ThreadErrorCallback gameStopper, Configuration configuration, CameraPositionCalculation cameraCalculation,
			Player myPlayer, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, GameMain main, MusicPlayer jumpMusicPlayer) {
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, gameStopper, world, networkDispatcher, sendControl, configuration);

		PlayerMovementCalculationFactory factory = CommonGameThreadFactory.createMovementCalculationFactory(world, jumpMusicPlayer);
		GameStepController stepController = CommonGameThreadFactory.createStepController(cameraCalculation, world, stateDispatcher, factory, sendControl,
				configuration);

		return CommonGameThreadFactory.create(stepController, gameStopper);
	}

	public static GameThread create(GameStepController worldController, ThreadErrorCallback errorCallback) {
		ThreadLoop threadLoop = new ThreadLoop(new GameThreadStep(worldController), MAX_GAME_LOOPS_PER_SECOND);
		return new GameThread(worldController, threadLoop, errorCallback);
	}

	public static GameStepController createStepController(CameraPositionCalculation cameraCalculation, World world, PlayerStateDispatcher stateDispatcher,
			PlayerMovementCalculationFactory factory, NetworkMessageDistributor sendControl, Configuration configuration) {
		return GameStepControllerFactory.create(cameraCalculation, world, stateDispatcher, factory, sendControl, configuration);
	}

	public static PlayerMovementCalculationFactory createMovementCalculationFactory(World world, MusicPlayer jumpMusicPlayer) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, jumpMusicPlayer);
	}

	private static void initInputServices(GameMain main, ThreadErrorCallback gameStopper, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, Configuration configuration) {
		NetworkListeners.allNetworkListeners(networkDispatcher, world, gameStopper, main, configuration);
	}
}
