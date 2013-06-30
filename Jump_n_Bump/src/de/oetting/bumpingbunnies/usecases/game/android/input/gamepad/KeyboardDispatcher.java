package de.oetting.bumpingbunnies.usecases.game.android.input.gamepad;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;

public class KeyboardDispatcher extends InputDispatcher<KeyboardInputService> {

	public KeyboardDispatcher(KeyboardInputService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		return false;
	}

	@Override
	public boolean dispatchControlViewTouch(View v, MotionEvent motion) {
		return getInputService().onButtonTouch(v, motion);
	}

	@Override
	public void dispatchOnKeyDown(int keyCode, KeyEvent event) {
	}

	@Override
	public void dispatchOnKeyUp(int keyCode, KeyEvent event) {
	}

}
