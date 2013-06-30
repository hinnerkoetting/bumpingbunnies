package de.oetting.bumpingbunnies.usecases.game.android.input.touchRelease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class TouchReleaseFactory extends
		AbstractPlayerInputServicesFactory<TouchReleaseInputService> {

	@Override
	public TouchReleaseInputService createInputService(AllPlayerConfig config,
			Context context) {
		TouchReleaseInputService service = new TouchReleaseInputService(
				config.getTabletControlledPlayerMovement(),
				config.getCoordinateCalculations());
		config.getGameView().addOnSizeListener(service);
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
