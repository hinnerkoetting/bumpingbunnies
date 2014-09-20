package de.oetting.bumpingbunnies.android.input;

import de.oetting.bumpingbunnies.core.input.InputService;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Dispatches user input from screen to right input service
 * 
 */
public abstract class InputDispatcher<S extends InputService> {

	private S inputService;

	public InputDispatcher(S service) {
		this.inputService = service;
	}

	protected S getInputService() {
		return this.inputService;
	}

	public abstract boolean dispatchGameTouch(MotionEvent motion);

	public boolean dispatchControlViewTouch(View v, MotionEvent motion) {
		return false;
	}

	public boolean dispatchOnKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean dispatchOnKeyUp(int keyCode, KeyEvent event) {
		return false;
	}
}
