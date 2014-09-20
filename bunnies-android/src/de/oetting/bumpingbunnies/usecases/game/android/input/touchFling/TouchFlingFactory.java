package de.oetting.bumpingbunnies.usecases.game.android.input.touchFling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;

public class TouchFlingFactory extends
		AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		TouchFlingService service = new TouchFlingService(
				movement,
				calculations);
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
