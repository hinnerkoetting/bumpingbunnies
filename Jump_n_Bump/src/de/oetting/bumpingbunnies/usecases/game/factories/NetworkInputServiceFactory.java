package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NetworkInputServiceFactory extends AbstractInputServiceFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkInputServiceFactory.class);

	@Override
	public InputService create(NetworkReceiver reicerThread,
			PlayerMovementController movementController, World world) {
		LOGGER.info("Creating Network Input Service");
		Player player = movementController.getPlayer();
		NetworkInputService inputService = new NetworkInputService(
				reicerThread, player, MessageParserFactory.create());
		NetworkToGameDispatcher gameDispatcher = reicerThread
				.getGameDispatcher();
		gameDispatcher.addObserver(player.id(), inputService);
		return inputService;
	}

}
