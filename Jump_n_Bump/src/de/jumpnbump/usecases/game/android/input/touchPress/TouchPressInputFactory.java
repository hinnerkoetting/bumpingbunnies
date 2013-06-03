package de.jumpnbump.usecases.game.android.input.touchPress;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.touch.TouchInputDispatcher;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class TouchPressInputFactory extends
		AbstractPlayerInputServicesFactory<TouchPressInputService> {

	@Override
	public TouchPressInputService createInputService(PlayerConfigFactory config) {
		TouchPressInputService service = new TouchPressInputService(
				config.getTabletControlledPlayerMovement());
		config.getGameView().addOnSizeListener(service);
		return service;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			TouchPressInputService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
