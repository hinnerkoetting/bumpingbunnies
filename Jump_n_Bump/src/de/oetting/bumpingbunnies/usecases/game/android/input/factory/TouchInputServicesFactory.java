package de.oetting.bumpingbunnies.usecases.game.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;

public class TouchInputServicesFactory extends
		AbstractPlayerInputServicesFactory<TouchService> {

	@Override
	public TouchService createInputService(PlayerMovement movement, Context context, CoordinatesCalculation calculations) {
		TouchService touchService = new TouchService(movement, calculations);
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
