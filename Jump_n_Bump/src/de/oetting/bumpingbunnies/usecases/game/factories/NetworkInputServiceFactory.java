package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class NetworkInputServiceFactory extends AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkInputServiceFactory.class);

	@Override
	public InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world) {
		LOGGER.info("Creating Network Input Service");
		return BluetoothInputServiceFactory.createBluetoothInputService(
				reicerThread, movementController.getPlayer());
	}

}
