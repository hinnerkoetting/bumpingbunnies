package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BluetoothInputServiceFactory {

	public static InputService createBluetoothInputService(
			NetworkReceiver reicerThread, Player player) {
		NetworkInputService inputService = new NetworkInputService(
				reicerThread, player);
		NetworkToGameDispatcher gameDispatcher = reicerThread
				.getGameDispatcher();
		gameDispatcher.addObserver(player.id(), inputService);
		return inputService;
	}
}
