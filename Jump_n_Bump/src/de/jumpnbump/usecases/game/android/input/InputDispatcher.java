package de.jumpnbump.usecases.game.android.input;

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

	public void dispatchOnKeyDown(int keyCode, KeyEvent event) {
	}

	public void dispatchOnKeyUp(int keyCode, KeyEvent event) {
	}
}
