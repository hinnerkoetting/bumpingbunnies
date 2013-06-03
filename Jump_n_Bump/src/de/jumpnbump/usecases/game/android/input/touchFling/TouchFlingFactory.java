package de.jumpnbump.usecases.game.android.input.touchFling;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.touch.TouchInputDispatcher;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;

public class TouchFlingFactory extends
		AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(PlayerConfig config) {
		TouchFlingService service = new TouchFlingService(
				config.getTabletControlledPlayerMovement());
		config.getGameView().addOnSizeListener(service);
		return service;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			AbstractTouchService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {

	}

}
