package de.oetting.bumpingbunnies.core.input;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;

public class ConfigurableKeyboardInputFactory {

	public ConfigurableKeyboardInputService create(KeyboardInputConfiguration configuration, PlayerMovement movementController) {
		return new ConfigurableKeyboardInputService(configuration.getKeyLeft(), configuration.getKeyRight(), configuration.getKeyUp(), movementController);
	}
}
