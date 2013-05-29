package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.pointer.PointerInputServiceFactory;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;

public abstract class AbstractInputServicesFactory<S extends InputService> {

	private static final MyLog LOGGER = Logger
			.getLogger(AbstractInputServicesFactory.class);
	private static AbstractInputServicesFactory<? extends InputService> factorySingleton;

	public static void init(InputConfiguration inputConfiguration) {
		factorySingleton = create(inputConfiguration);
		LOGGER.info("factory is of type %s", factorySingleton.getClass()
				.getSimpleName());
	}

	private static AbstractInputServicesFactory<? extends InputService> create(
			InputConfiguration inputConfiguration) {
		switch (inputConfiguration) {
		case KEYBOARD:
			return new KeyboardInputServicesFactory();
		case TOUCH:
			return new TouchInputServicesFactory();
		case TOUCH_WITH_UP:
			return new TouchJumpInputServicesFactory();
		case MULTI_TOUCH:
			return new MultiTouchJumpServicesFactory();
		case POINTER:
			return new PointerInputServiceFactory();
		}

		throw new IllegalArgumentException("Unknown Inputtype");
	}

	public static <S extends InputService> AbstractInputServicesFactory<S> getSingleton() {
		if (factorySingleton == null) {
			throw new IllegalArgumentException("Singleton not intialized");
		}
		return (AbstractInputServicesFactory<S>) factorySingleton;
	}

	public abstract S createInputService(PlayerConfigFactory config);

	public abstract InputDispatcher<?> createInputDispatcher(S inputService);

	/**
	 * Created View Controls for this type of Input. The child class is
	 * responsible to register all input view to the dispatcher. You do not have
	 * to worry about game view.
	 * 
	 * @param rootView
	 * @param inflater
	 * @param inputDispatcher
	 */
	public abstract void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher);
}
