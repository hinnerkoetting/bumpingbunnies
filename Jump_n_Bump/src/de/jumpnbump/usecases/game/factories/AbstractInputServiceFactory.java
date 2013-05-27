package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.NetworkInputService;
import de.jumpnbump.usecases.game.businesslogic.DummyInputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;

public abstract class AbstractInputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(AbstractInputServiceFactory.class);

	public abstract InputService create(InformationSupplier reicerThread,
			Player player);

	public static InputService createBluetoothInputService(
			InformationSupplier reicerThread, Player player) {
		NetworkInputService inputService = new NetworkInputService(player);
		reicerThread.addObserver(player.id(), inputService);
		return inputService;
	}

	public static InputService creatDummyInputService() {
		return new DummyInputService();
	}

}
