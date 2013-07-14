package de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;

public class HardwareKeyboardInputDispatcher extends
		InputDispatcher<HardwareKeyboardInputService> {

	public HardwareKeyboardInputDispatcher(HardwareKeyboardInputService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		return false;
	}

	public void onKeyUp(int keyCode, KeyEvent event) {
		getInputService().onKeyUp(keyCode, event);
	}

	public void onKeyDown(int keyCode, KeyEvent event) {
		getInputService().onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchOnKeyDown(int keyCode, KeyEvent event) {
		return getInputService().onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchOnKeyUp(int keyCode, KeyEvent event) {
		return getInputService().onKeyUp(keyCode, event);
	}

}
