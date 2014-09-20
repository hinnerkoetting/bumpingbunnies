package de.oetting.bumpingbunnies.usecases.game.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;

public abstract class AbstractPlayerInputServicesFactory<S extends InputService> {

	public abstract S createInputService(PlayerMovement movement, Context context, CoordinatesCalculation calculations);

	public abstract InputDispatcher<?> createInputDispatcher(S inputService);

	/**
	 * Created View Controls for this type of Input. The child class is responsible to register all input view to the dispatcher. You do not have to worry about
	 * game view.
	 * 
	 * @param rootView
	 * @param inflater
	 * @param inputDispatcher
	 */
	public abstract void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher);
}
