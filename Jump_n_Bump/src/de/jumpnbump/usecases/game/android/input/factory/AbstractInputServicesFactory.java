package de.jumpnbump.usecases.game.android.input.factory;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.dispatcher.InputDispatcher;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;

public abstract class AbstractInputServicesFactory<S extends InputService> {

	private static AbstractInputServicesFactory<?> factorySingleton;

	public static void init(InputConfiguration inputConfiguration) {
		factorySingleton = create(inputConfiguration);
	}

	private static AbstractInputServicesFactory<?> create(
			InputConfiguration inputConfiguration) {
		switch (inputConfiguration) {
		case KEYBOARD:
			return new KeyboardInputServicesFactory();
		case TOUCH:
			return new TouchInputServicesFactory();
		case TOUCH_WITH_UP:
			return new TouchJumpInputServicesFactory();
		}
		throw new IllegalArgumentException("Unknown Inputtype");
	}

	public static AbstractInputServicesFactory<?> getSingleton() {
		if (factorySingleton == null) {
			throw new IllegalArgumentException("Singleton not intialized");
		}
		return factorySingleton;
	}

	public abstract S createInputService(PlayerConfigFactory config);

	public abstract InputDispatcher<?> createInputDispatcher(S inputService);
}
