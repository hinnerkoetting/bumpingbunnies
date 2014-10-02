package de.oetting.bumpingbunnies.usecases.game.android.input.gamepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;

public class KeyboardInputServicesFactory extends
		AbstractPlayerInputServicesFactory<GamepadInputService> {

	@Override
	public GamepadInputService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		return new GamepadInputService(movement);
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
