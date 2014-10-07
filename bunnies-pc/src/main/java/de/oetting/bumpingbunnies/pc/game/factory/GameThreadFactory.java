package de.oetting.bumpingbunnies.pc.game.factory;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
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
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsRevived.PlayerIsRevivedReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointReceiver;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.StopGameReceiver;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class GameThreadFactory {

	public GameThread create(World world, GameStopper gameStopper, Configuration configuration, CameraPositionCalculation cameraCalculation, Player myPlayer,
			NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl, GameMain main) {
		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		initInputServices(main, gameStopper, world, networkDispatcher, sendControl);

		PlayerMovementCalculationFactory factory = createFactory(world);
		StateSenderFactory senderFactory = new DefaultStateSenderFactory(sendControl, myPlayer);
		GameStepController stepController = GameStepControllerFactory.create(cameraCalculation, world, stateDispatcher, factory, senderFactory, sendControl,
				configuration);

		return new GameThread(stepController);
	}

	private static void initInputServices(GameMain main, GameStopper gameStopper, World world, NetworkToGameDispatcher networkDispatcher,
			NetworkMessageDistributor sendControl) {
		addAllNetworkListeners(gameStopper, networkDispatcher, world);
	}

	private static void addAllNetworkListeners(GameStopper gameStopper, NetworkToGameDispatcher networkDispatcher, World world) {
		new StopGameReceiver(networkDispatcher, gameStopper);
		new PlayerIsDeadReceiver(networkDispatcher, world);
		new PlayerScoreReceiver(networkDispatcher, world);
		new PlayerIsRevivedReceiver(networkDispatcher, world);
		new SpawnPointReceiver(networkDispatcher, world);
	}

	private PlayerMovementCalculationFactory createFactory(World world) {
		CollisionDetection collisionDetection = new CollisionDetection(world);
		return new PlayerMovementCalculationFactory(new GameObjectInteractor(collisionDetection, world), collisionDetection, new DummyMusicPlayer());
	}
}
