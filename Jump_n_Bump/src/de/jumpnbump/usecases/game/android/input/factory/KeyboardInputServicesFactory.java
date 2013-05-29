package de.jumpnbump.usecases.game.android.input.factory;

import de.jumpnbump.usecases.game.android.input.dispatcher.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.dispatcher.KeyboardDispatcher;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class KeyboardInputServicesFactory extends
		AbstractInputServicesFactory<GamepadInputService> {

	@Override
	public GamepadInputService createInputService(PlayerConfigFactory config) {
		return config.createGamepadService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			GamepadInputService inputService) {
		return new KeyboardDispatcher(inputService);
	}

}
