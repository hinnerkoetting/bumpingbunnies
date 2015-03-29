package de.oetting.bumpingbunnies.android.input.multiTouch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class MultiTouchJumpServicesFactory extends AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(Player movement, Context context, CoordinatesCalculation calculations) {
		MultiTouchInputService touchService = new MultiTouchInputService(movement, calculations);
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(AbstractTouchService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView, LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
