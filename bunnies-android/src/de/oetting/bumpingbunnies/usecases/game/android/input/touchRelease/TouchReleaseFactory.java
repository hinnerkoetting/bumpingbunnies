package de.oetting.bumpingbunnies.usecases.game.android.input.touchRelease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;

public class TouchReleaseFactory extends
		AbstractPlayerInputServicesFactory<TouchReleaseInputService> {

	@Override
	public TouchReleaseInputService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		TouchReleaseInputService service = new TouchReleaseInputService(
				movement,
				calculations);
		return service;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			TouchReleaseInputService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
