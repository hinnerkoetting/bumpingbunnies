package de.oetting.bumpingbunnies.usecases.game.android.input.gamepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class KeyboardInputServicesFactory extends
		AbstractPlayerInputServicesFactory<GamepadInputService> {

	@Override
	public GamepadInputService createInputService(AllPlayerConfig config,
			Context context, GameView view, CoordinatesCalculation calculations) {
		return new GamepadInputService(
				config.getTabletControlledPlayerMovement());
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			GamepadInputService inputService) {
		return new KeyboardDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
		View v = inflater.inflate(R.layout.input_gamepad, rootView, true);
		registerTouchEvents(v, inputDispatcher);
	}

	private void registerTouchEvents(View v,
			final InputDispatcher<?> inputDispatcher) {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchControlViewTouch(v, event);
			}
		};
		OnTouchListener upTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchControlViewTouch(v, event);
			}
		};

		v.findViewById(R.id.button_down).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_up).setOnTouchListener(upTouchListener);
		v.findViewById(R.id.button_right).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_left).setOnTouchListener(touchListener);

	}

}
