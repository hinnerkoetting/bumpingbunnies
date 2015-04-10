package de.oetting.bumpingbunnies.android.input.pointer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.pathFinder.PathFinderFactory;
import de.oetting.bumpingbunnies.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PointerInputServiceFactory extends AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(Bunny movement, Context context, CoordinatesCalculation calculations) {
		PointerInputService touchService = new PointerInputService(movement, PathFinderFactory.createPathFinder(movement), calculations);
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
