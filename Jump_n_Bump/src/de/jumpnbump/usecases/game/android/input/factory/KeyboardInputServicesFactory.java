package de.jumpnbump.usecases.game.android.input.factory;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.jumpnbump.R;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.gamepad.KeyboardDispatcher;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class KeyboardInputServicesFactory extends
		AbstractPlayerInputServicesFactory<GamepadInputService> {

	@Override
	public GamepadInputService createInputService(PlayerConfigFactory config) {
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
		View v = inflater.inflate(R.layout.gamepad, rootView, true);
		registerTouchEvents(v, inputDispatcher);
	}

	private void registerTouchEvents(View v,
			final InputDispatcher<?> inputDispatcher) {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				inputDispatcher.dispatchControlViewTouch(v, event);
				return true;
			}
		};
		OnTouchListener upTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				inputDispatcher.dispatchControlViewTouch(v, event);
				return true;
			}
		};

		v.findViewById(R.id.button_down).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_up).setOnTouchListener(upTouchListener);
		v.findViewById(R.id.button_right).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_left).setOnTouchListener(touchListener);

	}

}
