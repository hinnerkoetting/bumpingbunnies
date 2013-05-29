package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.dispatcher.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.dispatcher.TouchInputDispatcher;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class TouchInputServicesFactory extends
		AbstractInputServicesFactory<TouchService> {

	@Override
	public TouchService createInputService(PlayerConfigFactory config) {
		return config.createTouchService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(TouchService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {

	}

}