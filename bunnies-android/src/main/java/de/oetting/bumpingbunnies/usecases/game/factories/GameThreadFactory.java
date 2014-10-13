package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.logic.CommonGameThreadFactory;
import de.oetting.bumpingbunnies.core.game.logic.GameThread;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.NetworkListeners;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(World world, Context context, Configuration configuration, CameraPositionCalculation cameraPositionCalculator,
			GameMain main, Player myPlayer, GameActivity activity, NetworkMessageDistributor sendControl, PlayerDisconnectedCallback callback) {

		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher(callback);
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, activity, world, networkDispatcher, sendControl, configuration);

		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, world);

		// Sending Coordinates Strep
		GameStepController worldController = GameStepControllerFactory.create(cameraPositionCalculator, world, stateDispatcher, factory, sendControl,
				configuration);
		return createGameThread(worldController, activity);
	}

	private static GameThread createGameThread(GameStepController worldController, ThreadErrorCallback errorCallback) {
		return CommonGameThreadFactory.create(worldController, errorCallback);
	}

	private static void initInputServices(GameMain main, ThreadErrorCallback activity, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, Configuration configuration) {
		NetworkListeners.allNetworkListeners(networkDispatcher, world, activity, main, configuration);
		main.setReceiveControl(createNetworkReceiveThreads(networkDispatcher, sendControl, configuration));
	}

	private static NetworkReceiveControl createNetworkReceiveThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			Configuration configuration) {
		return NetworkReceiveControlFactory.create(networkDispatcher, sendControl, configuration);
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context, World world) {
		CollisionDetection colDetection = new CollisionDetection(world);
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(colDetection, world), colDetection, musicPlayer);
	}

}
