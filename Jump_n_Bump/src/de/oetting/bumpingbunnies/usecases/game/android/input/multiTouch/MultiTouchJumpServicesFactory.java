package de.oetting.bumpingbunnies.usecases.game.android.input.multiTouch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class MultiTouchJumpServicesFactory extends
		AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(AllPlayerConfig config,
			Context context) {
		MultiTouchInputService touchService = new MultiTouchInputService(
				config.getTabletControlledPlayerMovement(),
				config.getCoordinateCalculations());
		config.getGameView().addOnSizeListener(touchService);
		return touchService;
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
