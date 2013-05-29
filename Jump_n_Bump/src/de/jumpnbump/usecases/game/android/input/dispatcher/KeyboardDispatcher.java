package de.jumpnbump.usecases.game.android.input.dispatcher;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;

public class KeyboardDispatcher extends InputDispatcher<GamepadInputService> {

	public KeyboardDispatcher(GamepadInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
	}

	@Override
	public void dispatchViewTouch(View v, MotionEvent motion) {
		getInputService().onButtonTouch(v, motion);
	}

}
