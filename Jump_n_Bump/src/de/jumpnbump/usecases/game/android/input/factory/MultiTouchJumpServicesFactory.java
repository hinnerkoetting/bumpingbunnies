package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.multiTouch.MultiTouchInputDispatcher;
import de.jumpnbump.usecases.game.android.input.multiTouch.MultiTouchInputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class MultiTouchJumpServicesFactory extends
		AbstractInputServicesFactory<MultiTouchInputService> {

	@Override
	public MultiTouchInputService createInputService(PlayerConfigFactory config) {
		return config.createMultiTouchService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			MultiTouchInputService inputService) {
		return new MultiTouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
