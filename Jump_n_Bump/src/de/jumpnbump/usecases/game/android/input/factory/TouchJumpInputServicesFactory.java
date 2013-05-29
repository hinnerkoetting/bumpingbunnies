package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.dispatcher.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.dispatcher.TouchWithJumpInputDispatcher;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class TouchJumpInputServicesFactory extends
		AbstractInputServicesFactory<TouchWithJumpService> {

	@Override
	public TouchWithJumpService createInputService(PlayerConfigFactory config) {
		return config.createTouchWithJumpService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			TouchWithJumpService inputService) {
		return new TouchWithJumpInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
