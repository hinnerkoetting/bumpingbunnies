package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;

public class NetworkInputServiceFactory extends AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(NetworkInputServiceFactory.class);

	@Override
	public InputService create(InformationSupplier reicerThread, Player player) {
		LOGGER.info("Creating Bluetooth Input Service");
		return createBluetoothInputService(reicerThread, player);
	}

}
