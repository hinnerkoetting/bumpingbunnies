package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.android.input.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.DummyStateSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.StateSenderFactory;

public class UserInputFactory {

	public static TouchService createTouch(PlayerMovementController playerMovement,
			BluetoothSocket socket, GameView view) {
		return TouchServiceFactory.create(playerMovement, view, socket);
	}

	public static TouchWithJumpService createTouchWithJump(
			PlayerMovementController playerMovement, BluetoothSocket socket,
			GameView view) {
		TouchWithJumpService touchService;
		if (socket != null) {
			StateSender sender = StateSenderFactory.createNetworkSender(socket);
			touchService = new TouchWithJumpService(playerMovement, sender);
		} else {
			touchService = new TouchWithJumpService(playerMovement,
					new DummyStateSender());
		}
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static GamepadInputService createGamepad(
			PlayerMovementController playerMovement, BluetoothSocket socket,
			Context context) {
		if (socket != null) {
			return createGamepadServiceWithBluetoothSending(playerMovement,
					socket, context);
		} else {
			return createGamepadServiceWithoutSending(playerMovement, socket);
		}
	}

	private static GamepadInputService createGamepadServiceWithoutSending(
			PlayerMovementController playerMovement, BluetoothSocket socket) {
		return new GamepadInputService(playerMovement, new DummyStateSender());
	}

	private static GamepadInputService createGamepadServiceWithBluetoothSending(
			PlayerMovementController playerMovement, BluetoothSocket socket,
			Context context) {
		StateSender sender = StateSenderFactory.createNetworkSender(socket);
		GamepadInputService inputservice = new GamepadInputService(
				playerMovement, sender);
		return inputservice;
	}
}
