package de.oetting.bumpingbunnies.core.game.steps.factory;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.game.spawnpoint.ListSpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.BunnyMovementStep;
import de.oetting.bumpingbunnies.core.game.steps.ClientBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.GameStepController;
import de.oetting.bumpingbunnies.core.game.steps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviver;
import de.oetting.bumpingbunnies.core.game.steps.SendingCoordinatesStep;
import de.oetting.bumpingbunnies.core.input.UserInputStep;
import de.oetting.bumpingbunnies.core.input.factory.OpponentInputFactoryImpl;
import de.oetting.bumpingbunnies.core.networking.MessageSenderToNetworkDelegate;
import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.StateSenderFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

public class GameStepControllerFactory {

	public static GameStepController create(CameraPositionCalculation cameraPositionCalculator, World world, PlayerStateDispatcher stateDispatcher,
			PlayerMovementCalculationFactory factory, StateSenderFactory stateSenderFactory, NetworkMessageDistributor sendControl, Configuration configuration) {
		SpawnPointGenerator spawnPointGenerator = new ListSpawnPointGenerator(world.getSpawnPoints());
		PlayerReviver reviver = new PlayerReviver(new MessageSenderToNetworkDelegate(sendControl));
		BunnyKillChecker killChecker = createKillChecker(configuration, world, spawnPointGenerator, reviver, new CollisionDetection(world), sendControl);
		UserInputStep userInputStep = new UserInputStep(createInputServiceFactory(world, stateDispatcher));
		BunnyMovementStep movementStep = BunnyMovementStepFactory.create(killChecker, factory);
		SendingCoordinatesStep sendCoordinates = new SendingCoordinatesStep(stateSenderFactory);
		return new GameStepController(userInputStep, movementStep, sendCoordinates, reviver, cameraPositionCalculator);
	}

	private static OpponentInputFactory createInputServiceFactory(World world, PlayerStateDispatcher stateDispatcher) {
		return new OpponentInputFactoryImpl(world, stateDispatcher);
	}

	private static BunnyKillChecker createKillChecker(Configuration conf, World world, SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver,
			CollisionDetection collisionDetection, NetworkMessageDistributor sendControl) {
		if (conf.isHost()) {
			return new HostBunnyKillChecker(collisionDetection, world, spawnPointGenerator, reviver, new MessageSenderToNetworkDelegate(sendControl));
		} else {
			return new ClientBunnyKillChecker();
		}
	}
}
