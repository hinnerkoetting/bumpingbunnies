package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.android.input.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.network.DummyStateSender;
import de.jumpnbump.usecases.game.network.GameNetworkSendThread;

public class UserInputFactory {

	public static TouchService createTouch(GamePlayerController playerMovement,
			BluetoothSocket socket, GameView view) {
		return TouchServiceFactory.create(playerMovement, view, socket);
	}

	public static TouchWithJumpService createTouchWithJump(
			GamePlayerController playerMovement, BluetoothSocket socket,
			GameView view) {
		TouchWithJumpService touchService;
		if (socket != null) {
			GameNetworkSendThread gameNetworkSendThread = new GameNetworkSendThread(
					socket, view.getContext());
			touchService = new TouchWithJumpService(playerMovement,
					gameNetworkSendThread);
			gameNetworkSendThread.start();
		} else {
			touchService = new TouchWithJumpService(playerMovement,
					new DummyStateSender());
		}
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static GamepadInputService createGamepad(
			GamePlayerController playerMovement, BluetoothSocket socket,
			Context context) {
		if (socket != null) {
			return createGamepadServiceWithBluetoothSending(playerMovement,
					socket, context);
		} else {
			return createGamepadServiceWithoutSending(playerMovement, socket);
		}
	}

	private static GamepadInputService createGamepadServiceWithoutSending(
			GamePlayerController playerMovement, BluetoothSocket socket) {
		return new GamepadInputService(playerMovement, new DummyStateSender());
	}

	private static GamepadInputService createGamepadServiceWithBluetoothSending(
			GamePlayerController playerMovement, BluetoothSocket socket,
			Context context) {
		GameNetworkSendThread sendthread = new GameNetworkSendThread(socket,
				context);
		GamepadInputService inputservice = new GamepadInputService(
				playerMovement, sendthread);
		sendthread.start();
		return inputservice;
	}

}
