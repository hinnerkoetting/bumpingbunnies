package de.jumpnbump.usecases.game.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class HardwareKeyboardInputDispatcher extends
		InputDispatcher<HardwareKeyboardInputService> {

	public HardwareKeyboardInputDispatcher(HardwareKeyboardInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

	public void onKeyUp(int keyCode, KeyEvent event) {
		getInputService().onKeyUp(keyCode, event);
	}

	public void onKeyDown(int keyCode, KeyEvent event) {
		getInputService().onKeyDown(keyCode, event);
	}

	@Override
	public void dispatchOnKeyDown(int keyCode, KeyEvent event) {
		getInputService().onKeyDown(keyCode, event);
	}

	@Override
	public void dispatchOnKeyUp(int keyCode, KeyEvent event) {
		getInputService().onKeyUp(keyCode, event);

	}

}
