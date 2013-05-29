package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.jumpnbump.R;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpInputDispatcher;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpService;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class TouchJumpInputServicesFactory extends
		AbstractInputServicesFactory<TouchWithJumpService> {

	@Override
	public TouchWithJumpService createInputService(PlayerConfigFactory config) {
		return config.createTouchWithJumpService();
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
				inputDispatcher.dispatchControlViewTouch(v, event);
				return true;
			}
		};
		upButton.setOnTouchListener(touchListener);
	}
}
