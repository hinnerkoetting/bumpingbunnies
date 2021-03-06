package de.oetting.bumpingbunnies.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.android.input.touch.TouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class TouchInputServicesFactory extends AbstractPlayerInputServicesFactory<TouchService> {

	@Override
	public TouchService createInputService(Bunny movement, Context context, CoordinatesCalculation calculations) {
		TouchService touchService = new TouchService(movement, calculations);
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(TouchService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView, LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {

	}

}
