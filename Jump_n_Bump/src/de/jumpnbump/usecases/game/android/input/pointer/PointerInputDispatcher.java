package de.jumpnbump.usecases.game.android.input.pointer;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class PointerInputDispatcher extends
		InputDispatcher<PointerInputService> {

	public PointerInputDispatcher(PointerInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		getInputService().onMotionEvent(motion);
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

}
