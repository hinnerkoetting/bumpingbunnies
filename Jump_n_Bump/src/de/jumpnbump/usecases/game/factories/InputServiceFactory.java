package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.NetworkMovementService;
import de.jumpnbump.usecases.game.businesslogic.DummyMovementService;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.network.NetworkReceiveThread;

public class InputServiceFactory {

	private static final MyLog LOGGER = Logger
			.getLogger(InputServiceFactory.class);

	public static InputService create(BluetoothSocket socket, Player player,
			Context context) {
		if (socket != null) {
			LOGGER.info("Creating Bluetooth Input Service");
			return createBluetoothInputService(socket, player, context);
		} else {
			LOGGER.info("Creating Dummy Input Service");
			return creatDummyInputService();
		}
	}

	public static InputService createBluetoothInputService(
			BluetoothSocket socket, Player player, Context context) {
		NetworkReceiveThread receiveThread = new NetworkReceiveThread(socket,
				context);
		receiveThread.start();
		return new NetworkMovementService(receiveThread, player);
	}

	public static InputService creatDummyInputService() {
		return new DummyMovementService();
	}

}
