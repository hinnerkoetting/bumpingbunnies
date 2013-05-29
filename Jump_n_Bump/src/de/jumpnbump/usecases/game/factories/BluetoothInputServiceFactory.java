package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.network.NetworkInputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;

public class BluetoothInputServiceFactory {

	public static InputService createBluetoothInputService(
			InformationSupplier reicerThread, Player player) {
		NetworkInputService inputService = new NetworkInputService(player);
		reicerThread.addObserver(player.id(), inputService);
		return inputService;
	}
}
