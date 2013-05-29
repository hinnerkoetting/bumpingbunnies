package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.PathFinder.PathFinderFactory;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.multiTouch.MultiTouchInputService;
import de.jumpnbump.usecases.game.android.input.pointer.PointerInputService;
import de.jumpnbump.usecases.game.android.input.rememberPointer.RememberPointerInputService;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class UserInputFactory {

	public static TouchService createTouch(
			PlayerMovementController playerMovement, GameView view) {
		TouchService touchService = new TouchService(playerMovement);
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static TouchWithJumpService createTouchWithJump(
			PlayerMovementController playerMovement, GameView view) {
		TouchWithJumpService touchService = new TouchWithJumpService(
				playerMovement);
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static MultiTouchInputService createMultiTouch(
			PlayerMovementController playerMovement, GameView view) {
		MultiTouchInputService touchService = new MultiTouchInputService(
				playerMovement);
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static PointerInputService createPointer(
			PlayerMovementController playerMovement, GameView view) {
		PointerInputService touchService = new PointerInputService(
				playerMovement,
				PathFinderFactory.createPathFinder(playerMovement.getPlayer()));
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static RememberPointerInputService createRememberPointer(
			PlayerMovementController playerMovement, GameView view) {
		RememberPointerInputService touchService = new RememberPointerInputService(
				playerMovement,
				PathFinderFactory.createPathFinder(playerMovement.getPlayer()));
		view.addOnSizeListener(touchService);
		return touchService;
	}

	public static GamepadInputService createGamepad(
			PlayerMovementController playerMovement) {
		return new GamepadInputService(playerMovement);
	}

}
