package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.communication.AndroidOpponentTypeReceiveFactoryFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControlFactory;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
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
		return createGameThread(worldController);
	}

	private static GameThread createGameThread(GameStepController worldController) {
		return new GameThread(worldController);
	}

	private static void initInputServices(GameMain main, GameActivity activity, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl, Configuration configuration) {
		addAllNetworkListeners(activity, networkDispatcher, world);
		main.setReceiveControl(createNetworkReceiveThreads(networkDispatcher, sendControl, configuration));
	}

	private static void addAllNetworkListeners(GameActivity activity, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
	}

	private static NetworkReceiveControl createNetworkReceiveThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			Configuration configuration) {
		return NetworkReceiveControlFactory.create(networkDispatcher, sendControl, new AndroidOpponentTypeReceiveFactoryFactory(), configuration);
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context, World world) {
		CollisionDetection colDetection = new CollisionDetection(world);
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(colDetection, world), colDetection, musicPlayer);
	}

}
