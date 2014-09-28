package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.communication.AndroidStateSenderFactory;
import de.oetting.bumpingbunnies.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.main.GameThread;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.factory.GameStepControllerFactory;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.StrictNetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiveControl;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class GameThreadFactory {

	public static GameThread create(World world, Context context, Configuration configuration, CameraPositionCalculation cameraPositionCalculator,
			GameMain main, Player myPlayer, GameActivity activity, NetworkMessageDistributor sendControl) {

		NetworkToGameDispatcher networkDispatcher = new StrictNetworkToGameDispatcher();
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, activity, world, networkDispatcher, sendControl);

		PlayerMovementCalculationFactory factory = createMovementCalculationFactory(context, world);

		// Sending Coordinates Strep
		GameStepController worldController = GameStepControllerFactory.create(cameraPositionCalculator, world, stateDispatcher, factory,
				new AndroidStateSenderFactory(sendControl, myPlayer), sendControl, configuration);
		return createGameThread(worldController);
	}

	private static GameThread createGameThread(GameStepController worldController) {
		return new GameThread(worldController);
	}

	private static void initInputServices(GameMain main, GameActivity activity, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		addAllNetworkListeners(activity, networkDispatcher, world);
		main.setReceiveControl(createNetworkReceiveThreads(networkDispatcher, sendControl));
	}

	private static void addAllNetworkListeners(GameActivity activity, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, activity);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
	}

	private static NetworkReceiveControl createNetworkReceiveThreads(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		return createNetworkReceiveControl(networkDispatcher, sendControl);
	}

	private static NetworkReceiveControl createNetworkReceiveControl(NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		NetworkReceiveThreadFactory threadFactory = new NetworkReceiveThreadFactory(SocketStorage.getSingleton(), networkDispatcher, sendControl);
		NetworkReceiveControl receiveControl = new NetworkReceiveControl(threadFactory);
		return receiveControl;
	}

	private static PlayerMovementCalculationFactory createMovementCalculationFactory(Context context, World world) {
		CollisionDetection colDetection = new CollisionDetection(world);
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(colDetection, world), colDetection, musicPlayer);
	}

}
