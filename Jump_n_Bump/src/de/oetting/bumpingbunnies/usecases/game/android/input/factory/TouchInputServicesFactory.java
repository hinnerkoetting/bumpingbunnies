package de.oetting.bumpingbunnies.usecases.game.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class TouchInputServicesFactory extends
		AbstractPlayerInputServicesFactory<TouchService> {

	@Override
	public TouchService createInputService(AllPlayerConfig config, Context context) {
		TouchService touchService = new TouchService(
				config.getTabletControlledPlayerMovement(),
				config.getCoordinateCalculations());
		config.getGameView().addOnSizeListener(touchService);
		return touchService;
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