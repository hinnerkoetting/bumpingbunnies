package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NetworkInputServiceFactory implements OtherPlayerInputServiceFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NetworkInputServiceFactory.class);

	@Override
	public OtherPlayerInputService create(PlayerMovementController movementController,
			World world) {
		LOGGER.info("Creating Network Input Service");
		Player player = movementController.getPlayer();
		PlayerFromNetworkInput inputService = new PlayerFromNetworkInput(
				player);
		return inputService;
	}

}
