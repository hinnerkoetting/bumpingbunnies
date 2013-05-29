package de.jumpnbump.usecases.game.android.input.dispatcher;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputService;

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

	public abstract void dispatchViewTouch(View v, MotionEvent motion);
}
