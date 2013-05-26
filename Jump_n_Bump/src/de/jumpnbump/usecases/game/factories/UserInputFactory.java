package de.jumpnbump.usecases.game.factories;

import android.bluetooth.BluetoothSocket;
import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.network.DummyStateSender;
import de.jumpnbump.usecases.game.network.GameNetworkSendThread;

public class UserInputFactory {

	public static InputService createTouch(GamePlayerController playerMovement,
			BluetoothSocket socket, GameView view) {
		return TouchServiceFactory.create(playerMovement, view, socket);
	}

	public static GamepadInputService createGamepad(
			GamePlayerController playerMovement, BluetoothSocket socket) {
		if (socket != null) {
			return createGamepadServiceWithBluetoothSending(playerMovement,
					socket);
		} else {
			return createGamepadServiceWithoutSending(playerMovement, socket);
		}
	}

	private static GamepadInputService createGamepadServiceWithoutSending(
			GamePlayerController playerMovement, BluetoothSocket socket) {
		return new GamepadInputService(playerMovement, new DummyStateSender());
	}

	private static GamepadInputService createGamepadServiceWithBluetoothSending(
			GamePlayerController playerMovement, BluetoothSocket socket) {
		GameNetworkSendThread sendthread = new GameNetworkSendThread(socket);
		GamepadInputService inputservice = new GamepadInputService(
				playerMovement, sendthread);
		sendthread.start();
		return inputservice;
	}

}
