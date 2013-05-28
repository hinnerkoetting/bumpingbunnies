package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.TouchService;
import de.jumpnbump.usecases.game.android.input.TouchWithJumpService;
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

	public static GamepadInputService createGamepad(
			PlayerMovementController playerMovement) {
		return new GamepadInputService(playerMovement);
	}

}
