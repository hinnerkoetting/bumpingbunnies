package de.jumpnbump.usecases.game.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.AllPlayerConfig;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;

public abstract class AbstractPlayerInputServicesFactory<S extends InputService> {

	private static final MyLog LOGGER = Logger
			.getLogger(AbstractPlayerInputServicesFactory.class);
	private static AbstractPlayerInputServicesFactory<? extends InputService> factorySingleton;

	public static void init(InputConfiguration inputConfiguration) {
		factorySingleton = create(inputConfiguration);
		LOGGER.info("factory is of type %s", factorySingleton.getClass()
				.getSimpleName());
	}

	private static AbstractPlayerInputServicesFactory<? extends InputService> create(
			InputConfiguration inputConfiguration) {
		return inputConfiguration.createInputconfigurationClass();
	}

	public static <S extends InputService> AbstractPlayerInputServicesFactory<S> getSingleton() {
		if (factorySingleton == null) {
			throw new IllegalArgumentException("Singleton not intialized");
		}
		return (AbstractPlayerInputServicesFactory<S>) factorySingleton;
	}

	public abstract S createInputService(AllPlayerConfig config, Context context);

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
