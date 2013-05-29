package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.World;

public class NetworkInputServiceFactory extends AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkInputServiceFactory.class);

	@Override
	public InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world) {
		LOGGER.info("Creating Bluetooth Input Service");
		return BluetoothInputServiceFactory.createBluetoothInputService(
				reicerThread, movementController.getPlayer());
	}

}
