package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.communication.DummyStateSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.StateSenderFactory;

public class TouchServiceFactory {

	public static TouchService create(GamePlayerController playerMovent,
			GameView gameView, BluetoothSocket socket) {
		if (socket != null) {
			return createBluetoothSendingTouchService(playerMovent, gameView,
					socket);
		} else {
			return createNoSendingTouchService(playerMovent, gameView);
		}
	}

	public static TouchService createBluetoothSendingTouchService(
			GamePlayerController playerMovent, GameView gameView,
			BluetoothSocket socket) {

		StateSender sender = StateSenderFactory.createNetworkSender(socket);
		TouchService touchService = new TouchService(playerMovent, sender);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}

	public static TouchService createNoSendingTouchService(
			GamePlayerController playerMovent, GameView gameView) {
		DummyStateSender dummysender = new DummyStateSender();
		TouchService touchService = new TouchService(playerMovent, dummysender);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}
}
