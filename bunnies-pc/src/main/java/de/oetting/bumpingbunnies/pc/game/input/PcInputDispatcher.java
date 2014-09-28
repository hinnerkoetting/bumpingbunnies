package de.oetting.bumpingbunnies.pc.game.input;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;
import de.oetting.bumpingbunnies.core.input.ConfigurableKeyboardInputService;

public class PcInputDispatcher {

	private final List<ConfigurableKeyboardInputService> inputServices;

	public PcInputDispatcher() {
		inputServices = new ArrayList<>();
	}

	public void dispatchOnKeyDown(KeyCode keyCode) {
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyDown(keyCode.getName());
	}

	public void dispatchOnKeyUp(KeyCode keyCode) {
		for (ConfigurableKeyboardInputService inputService : inputServices)
			inputService.onKeyUp(keyCode.getName());
	}

	public void addInputService(ConfigurableKeyboardInputService inputService) {
		inputServices.add(inputService);
	}
}
