package de.oetting.bumpingbunnies.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;

public class HardwareKeyboardInputDispatcher extends
		InputDispatcher<HardwareKeyboardInputService> {

	public HardwareKeyboardInputDispatcher(HardwareKeyboardInputService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		return false;
	}

	@Override
	public boolean dispatchOnKeyDown(int keyCode, KeyEvent event) {
		return getInputService().onKeyDown(keyCode);
	}

	@Override
	public boolean dispatchOnKeyUp(int keyCode, KeyEvent event) {
		return getInputService().onKeyUp(keyCode);
	}

}
