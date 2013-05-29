package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.network.NetworkInputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public abstract class AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(AbstractInputServiceFactory.class);

	public abstract InputService create(InformationSupplier reicerThread,
			PlayerMovementController movementController, World world);

	public static InputService createBluetoothInputService(
			InformationSupplier reicerThread, Player player) {
		NetworkInputService inputService = new NetworkInputService(player);
		reicerThread.addObserver(player.id(), inputService);
		return inputService;
	}

}
