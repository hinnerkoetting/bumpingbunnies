package de.oetting.bumpingbunnies.core.input;

import de.oetting.bumpingbunnies.model.configuration.input.KeyboardInputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class ConfigurableKeyboardInputFactory {

	public ConfigurableKeyboardInputService create(KeyboardInputConfiguration configuration, Player movementController) {
		return new ConfigurableKeyboardInputService(configuration.getKeyLeft(), configuration.getKeyRight(), configuration.getKeyUp(), movementController);
	}
}
