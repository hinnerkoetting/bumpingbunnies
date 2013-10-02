package de.oetting.bumpingbunnies.usecases.game.android.input.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchWithJumpInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchWithJumpService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;

public class TouchJumpInputServicesFactory extends
		AbstractPlayerInputServicesFactory<TouchWithJumpService> {

	@Override
	public TouchWithJumpService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		TouchWithJumpService touchService = new TouchWithJumpService(
				movement,
				calculations);
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			TouchWithJumpService inputService) {
		return new TouchWithJumpInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, final InputDispatcher<?> inputDispatcher) {
		View view = inflater.inflate(R.layout.input_up, rootView, true);
		View upButton = view.findViewById(R.id.button_up);
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchControlViewTouch(v, event);
			}
		};
		upButton.setOnTouchListener(touchListener);
	}
}
