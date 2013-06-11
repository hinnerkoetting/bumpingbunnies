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

	public abstract void dispatchGameTouch(MotionEvent motion);

	public abstract void dispatchControlViewTouch(View v, MotionEvent motion);

	public abstract void dispatchOnKeyDown(int keyCode, KeyEvent event);

	public abstract void dispatchOnKeyUp(int keyCode, KeyEvent event);
}
