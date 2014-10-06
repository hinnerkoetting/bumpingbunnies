package de.oetting.bumpingbunnies.android.input.analog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class AnalogInputFactory extends AbstractPlayerInputServicesFactory<AnalogInputService> {

	@Override
	public AnalogInputService createInputService(PlayerMovement movement, Context context, CoordinatesCalculation calculations) {
		AnalogInputService touchService = new AnalogInputService(movement);
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(AnalogInputService inputService) {
		return new AnalogInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView, LayoutInflater inflater, final InputDispatcher<?> inputDispatcher) {
		View controlView = inflater.inflate(R.layout.input_analog_control, rootView, true);
		View analogInput = controlView.findViewById(R.id.analog_input);
		analogInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				inputDispatcher.dispatchControlViewTouch(v, event);
				return true;
			}
		});
	}
}
