package de.oetting.bumpingbunnies.usecases.game.android.input.analog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class AnalogInputFactory extends
		AbstractPlayerInputServicesFactory<AnalogInputService> {

	@Override
	public AnalogInputService createInputService(AllPlayerConfig config,
			Context context, GameView view) {
		AnalogInputService touchService = new AnalogInputService(
				config.getTabletControlledPlayerMovement());
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			AnalogInputService inputService) {
		return new AnalogInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, final InputDispatcher<?> inputDispatcher) {
		View controlView = inflater.inflate(R.layout.input_analog_control,
				rootView, true);
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
