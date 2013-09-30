package de.oetting.bumpingbunnies.usecases.game.android.input.touchPress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class TouchPressInputFactory extends
		AbstractPlayerInputServicesFactory<TouchPressInputService> {

	@Override
	public TouchPressInputService createInputService(AllPlayerConfig config,
			Context context, GameView view, CoordinatesCalculation calculations) {
		TouchPressInputService service = new TouchPressInputService(
				config.getTabletControlledPlayerMovement(),
				calculations);
		view.addOnSizeListener(service);
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
