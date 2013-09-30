package de.oetting.bumpingbunnies.usecases.game.android.input.touchRelease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class TouchReleaseFactory extends
		AbstractPlayerInputServicesFactory<TouchReleaseInputService> {

	@Override
	public TouchReleaseInputService createInputService(AllPlayerConfig config,
			Context context, GameView view, CoordinatesCalculation calculations) {
		TouchReleaseInputService service = new TouchReleaseInputService(
				config.getTabletControlledPlayerMovement(),
				calculations);
		view.addOnSizeListener(service);
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
