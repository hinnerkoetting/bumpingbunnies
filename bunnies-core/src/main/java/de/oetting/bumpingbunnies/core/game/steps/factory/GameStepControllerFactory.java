package de.oetting.bumpingbunnies.core.game.steps.factory;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.spawnpoint.RandomizedSpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviver;
import de.oetting.bumpingbunnies.core.game.steps.ScoreboardSynchronisation;
import de.oetting.bumpingbunnies.core.input.UserInputStep;
import de.oetting.bumpingbunnies.core.input.factory.OpponentInputFactoryImpl;
import de.oetting.bumpingbunnies.core.network.MessageSenderToNetworkDelegate;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.world.World;

public class GameStepControllerFactory {

	public static GameStepController create(CameraPositionCalculation cameraPositionCalculator, World world,
			PlayerStateDispatcher stateDispatcher, PlayerMovementCalculationFactory factory,
			NetworkMessageDistributor sendControl, Configuration configuration,
			PlayerDisconnectedCallback disconnectCallback, MusicPlayer musicPlayer, GameStopper gameStopper, ScoreboardSynchronisation scoreSynchronisation) {
		SpawnPointGenerator spawnPointGenerator = new RandomizedSpawnPointGenerator(world.getSpawnPoints());
		PlayerReviver reviver = new PlayerReviver(new MessageSenderToNetworkDelegate(sendControl));
		BunnyKillChecker killChecker = createKillChecker(configuration, world, spawnPointGenerator, reviver,
				new CollisionDetection(world), sendControl, disconnectCallback, musicPlayer, gameStopper, scoreSynchronisation);
		UserInputStep userInputStep = new UserInputStep(
				createInputServiceFactory(world, stateDispatcher, configuration));
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(killChecker, factory, world);
		return new GameStepController(userInputStep, movementStep, reviver, cameraPositionCalculator);
	}

	private static OpponentInputFactory createInputServiceFactory(World world, PlayerStateDispatcher stateDispatcher,
			Configuration configuration) {
		return new OpponentInputFactoryImpl(world, stateDispatcher, configuration);
	}

	private static BunnyKillChecker createKillChecker(Configuration conf, World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, CollisionDetection collisionDetection,
			NetworkMessageDistributor sendControl, PlayerDisconnectedCallback disconnectCallback,
			MusicPlayer musicPlayer, GameStopper gamestopper, ScoreboardSynchronisation scoreSynchronisation) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(collisionDetection, world, spawnPointGenerator, reviver,
					new MessageSenderToNetworkDelegate(sendControl), disconnectCallback, musicPlayer, gamestopper, conf, scoreSynchronisation);
		} else {
			return new ClientBunnyKillChecker();
		}
	}
}
