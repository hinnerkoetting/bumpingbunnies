package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchServiceFactory {

	public static TouchService create(PlayerMovementController playerMovent,
			GameView gameView, BluetoothSocket socket) {
		TouchService touchService = new TouchService(playerMovent);
		gameView.addOnSizeListener(touchService);
		return touchService;
	}

}
