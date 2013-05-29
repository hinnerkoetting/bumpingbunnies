package de.jumpnbump.usecases.game.android.input.gamepad;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class KeyboardDispatcher extends InputDispatcher<GamepadInputService> {

	public KeyboardDispatcher(GamepadInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
		getInputService().onButtonTouch(v, motion);
	}

}
