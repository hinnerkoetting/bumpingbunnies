package de.oetting.bumpingbunnies.pc.game.input;

import javafx.scene.input.KeyCode;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;

public class PcInputDispatcher {

	private final ConfigurableKeyboardInputService inputService;

	public PcInputDispatcher(ConfigurableKeyboardInputService inputService) {
		this.inputService = inputService;
	}

	public boolean dispatchOnKeyDown(KeyCode keyCode) {
		return inputService.onKeyDown(keyCode.getName());
	}

	public boolean dispatchOnKeyUp(KeyCode keyCode) {
		return inputService.onKeyUp(keyCode.getName());
	}
}
