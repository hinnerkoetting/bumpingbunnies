package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothInputServiceFactory {

	public static InputService createBluetoothInputService(
			InformationSupplier reicerThread, Player player) {
		NetworkInputService inputService = new NetworkInputService(
				reicerThread, player);
		reicerThread.addObserver(player.id(), inputService);
		return inputService;
	}
}
